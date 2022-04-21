package load

import org.apache.spark.sql.{DataFrame, SaveMode}
import spark.Spark

case class DataLoader() {

  def getDataFrameFromDB2OnCloud(): Option[DataFrame] = {
    val conf = Spark.sparkSession.conf
    try {
      val df = Spark.sparkSession.read.format(conf.get("driver_type")).
        option("url", conf.get("spark.db2.url")).
        option("dbtable", conf.get("spark.db2.dbtable")).
        option("driver", conf.get("driver")).
        option("lowerBound", 2015).
        option("upperBound", 2018).
        option("partitionColumn", "YEAR").
        option("numPartitions", 10).
        load()
      df.show()
      Some(df)
    } catch {
      case e: Exception => {
        println("ERROR: Reading data fom DB2 failed")
        e.printStackTrace()
        None
      }
    }
  }

  def saveCSV(df: DataFrame): Unit = {
    val savingType:String = Spark.sparkSession.conf.get("spark.save.type")
    savingType match {
      case "cos" => saveCSVToCOS(df)
      case "fs" => saveCSVtoFs(df)
      case _ => {
        println("ERROR:Invalid type for saving data.")
        System.exit(1)
      }
    }
  }


  def saveCSVToCOS(df: DataFrame): Unit = {
    try {
      val sparkConf = Spark.sparkSession.conf
      val bucket = sparkConf.get("spark.path")
      val fileName = sparkConf.get("spark.fileName")
      Spark.configureCOS()
      df.write.mode(SaveMode.Overwrite).format("csv").save("cos://" + bucket + ".service/" + fileName)
    } catch {
      case e: NoSuchElementException => {
        println("ERROR:COS properties not found")
        e.printStackTrace()
        System.exit(1)
      }
      case e: Exception => {
        println("Error in saveCSVToCOS method")
        e.printStackTrace()
        System.exit(1)
      }
    }
  }

  def saveCSVtoFs(df: DataFrame): Unit = {
    val conf = Spark.sparkSession.conf
    df.write.option("header", true)
      .mode(SaveMode.Overwrite)
      .partitionBy("YEAR")
      .csv(conf.get("spark.path")+"\\" + conf.get("spark.fileName"))
  }
}
