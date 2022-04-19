package sparkConfig

import connection.Properties
import org.apache.spark.sql.SparkSession

case class SparkConf(){
  /**
   * This class is used to config Spark
   */

  val url = Properties.CONNECTION_URL
  val table = Properties.TABLE
  val driver = Properties.DRIVER
  val driver_type = Properties.DRIVER_TYPE

  /**
   * This method returns Spark Session, parameters of which contain properties for connecting to DB2 on Cloud.
   * @return configured SparkSession
   */
  def getSparkSession(): SparkSession = {
      val spark = SparkSession
        .builder()
        .master("local[3]")
        .appName("Spark SQL basic example")
        .config("driver", driver)
        .config("url", url)
        .config("dbtable", table)
        .config("driver_type", driver_type)
        .getOrCreate()
      spark
   }

  /**
   * This method creates configuration for Stocator to connect to the Cloud Object Storage.
   */
  def configureCOS():Unit = {
      val spark = SparkConf().getSparkSession()
      spark.sparkContext.hadoopConfiguration.set("fs.stocator.scheme.list", "cos")
      spark.sparkContext.hadoopConfiguration.set("fs.cos.impl", "com.ibm.stocator.fs.ObjectStoreFileSystem")
      spark.sparkContext.hadoopConfiguration.set("fs.stocator.cos.impl", "com.ibm.stocator.fs.cos.COSAPIClient")
      spark.sparkContext.hadoopConfiguration.set("fs.stocator.cos.scheme", "cos")
      spark.sparkContext.hadoopConfiguration.set("fs.cos.service.access.key", spark.conf.get("spark.access.key"))
      spark.sparkContext.hadoopConfiguration.set("fs.cos.service.secret.key", spark.conf.get("spark.secret.key"))
      spark.sparkContext.hadoopConfiguration.set("fs.cos.service.endpoint", spark.conf.get("spark.endpoint"))
  }
}
