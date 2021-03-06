package spark

import org.apache.spark.sql.SparkSession

/**
 * This object is used to config Spark and get SparkSession.
 * It sets driver for connecting to DB2 on Cloud
 */
object Spark {

  val sparkSession = SparkSession
    .builder()
    .appName("Spark Practice")
    .config("driver_type", "jdbc")
    .getOrCreate()

  /**
   * This method creates configuration for Stocator to connect to the Cloud Object Storage.
   */
  def configureCOS():Unit = {
    sparkSession.sparkContext.hadoopConfiguration.set("fs.stocator.scheme.list", "cos")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.cos.impl", "com.ibm.stocator.fs.ObjectStoreFileSystem")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.stocator.cos.impl", "com.ibm.stocator.fs.cos.COSAPIClient")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.stocator.cos.scheme", "cos")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.cos.service.access.key", sparkSession.conf.get("spark.access.key"))
    sparkSession.sparkContext.hadoopConfiguration.set("fs.cos.service.secret.key", sparkSession.conf.get("spark.secret.key"))
    sparkSession.sparkContext.hadoopConfiguration.set("fs.cos.service.endpoint", sparkSession.conf.get("spark.endpoint"))
  }

}

