package load.impl

import load.DataLoader
import org.apache.spark.sql.{DataFrame, SaveMode}
import sparkConfig.SparkConf

case class DataLoaderImpl() extends DataLoader {

  override def getDataFrameFromDB2OnCloud(): Option[DataFrame] = {
    val spark = SparkConf().getSparkSession()
    val conf = spark.conf
    try {
      val df = spark.read.format(spark.conf.get("driver_type")).
        option("url", conf.get("url")).
        option("dbtable", conf.get("dbtable")).
        option("driver", conf.get("driver")).
        option("lowerBound", 2015).
        option("upperBound", 2018).
        option("partitionColumn", "YEAR").
        option("numPartitions", 10).
        load()
      Some(df)
    } catch {
      case e: Exception => {
        println("ERROR: Reading data fom DB2 failed")
        None
      }
    }
  }

  override def saveCSVToCOS(df: DataFrame): Unit = {
    try {
      val sparkConf = SparkConf().getSparkSession().conf
      val bucket = sparkConf.get("spark.bucket")
      val fileName = sparkConf.get("spark.fileName")
      SparkConf().configureCOS()
      df.write.mode(SaveMode.Overwrite).format("csv").save("cos://" + bucket + ".service/" + fileName)
    } catch {
      case e: NoSuchElementException => {
        println("ERROR:COS properties not found")
        System.exit(1)
      }
      case e: Exception => {
        println("Error in saveCSVToCOS method")
        System.exit(1)
      }
    }
  }

  override def saveToCSV(df: DataFrame, path: String): Unit = {
    df.write.option("header", true)
      .mode(SaveMode.Overwrite)
      .partitionBy("YEAR")
      .csv(path)
  }
}
