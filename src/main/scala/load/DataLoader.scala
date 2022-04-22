package load

import org.apache.spark.sql.{DataFrame, RuntimeConfig, SaveMode}
import spark.Spark

case class DataLoader() {

  def getData(): Option[DataFrame] = {
    val conf = Spark.sparkSession.conf
    val sourceType: String = conf.get("spark.source")
    val driver = getDriver(sourceType)
    val connectionUrl: String = prepareUrl(sourceType)
    try {
      val df = Spark.sparkSession.read.format(conf.get("driver_type")).
        option("url", connectionUrl).
        option("dbtable", conf.get("spark.dbtable")).
        option("driver", driver).
        option("lowerBound", 2015).
        option("upperBound", 2018).
        option("partitionColumn", "YEAR").
        option("numPartitions", 10).
        load()
      Some(df)
    } catch {
      case e: Exception => {
        println("ERROR in getData method.")
        e.printStackTrace()
        None
      }
    }
  }

  def prepareUrl(sourceType: String): String = {
    val conf = Spark.sparkSession.conf
    var url = conf.get("spark.source.url")
    val password = conf.get("spark.source.password")
    val username = conf.get("spark.source.username")
    sourceType match {
      case "mysql" => url += "?user=" + username + "&password=" + password
      case "db2" => url += ":user=" + username + ";password=" + password + ";sslConnection=true;"
      case _ => {
        println("ERROR:Invalid type of source.")
        System.exit(1)
      }
    }
    url
  }

  def getDriver(sourceType: String): String = {
    var driver = ""
    sourceType match {
      case "mysql" => driver = "com.mysql.cj.jdbc.Driver"
      case "db2" => driver = "com.ibm.db2.jcc.DB2Driver"
      case _ => {
        println("ERROR:Invalid type of source.")
        System.exit(1)
      }
    }
    driver
  }


  def saveCSV(df: DataFrame): Unit = {
    val savingType: String = Spark.sparkSession.conf.get("spark.save.type")
    savingType match {
      case "cos" => saveCSVToCOS(df)
      case "fs" => saveCSVtoFs(df)
      case _ => {
        println("ERROR:Invalid saving data type.")
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
      df.write.mode(SaveMode.Overwrite)
        .option("inferSchema", true)
        .option("header", true)
        .format("csv")
        .save("cos://" + bucket + ".service/" + fileName)
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
      .csv(conf.get("spark.path") + "\\" + conf.get("spark.fileName"))
  }
}
