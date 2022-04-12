package sparkConfig

import connection.Properties
import org.apache.spark.sql.SparkSession

case class SparkConf(){
  val url = Properties.CONNECTION_URL
  val table = Properties.TABLE
  val driver = Properties.DRIVER
  val driver_type = Properties.DRIVER_TYPE

  def getSparkSession(): SparkSession ={
     val spark =  SparkSession
       .builder()
       .appName("Spark SQL basic example")
       .config("driver", driver)
       .config("url", url)
       .config("dbtable", table)
       .config("driver_type", driver_type)
       .getOrCreate()
    spark
   }
}
