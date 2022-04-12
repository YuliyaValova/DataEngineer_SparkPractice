package load.impl

import entity.TableRow
import load.DataLoader
import org.apache.spark.sql.{DataFrame, SaveMode}
import sparkConfig.SparkConf

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.text.MessageFormat

//todo checks for issues
case class DataLoaderImpl() extends DataLoader{

  override def addTableRecord(tableName:String, connection: Connection, row:TableRow): Boolean = {
    val query = "insert into " + tableName + " values(" + row.toString + ");"
    val statement = connection.createStatement()
    statement.execute(query)
  }

  override def createTable(connection: Connection, tableName: String): Unit = {
    val query = """
                      |create table {0} (
                      |product_id int not null,
                      |product_group smallint,
                      |year int not null,
                      |month_1 int,
                      |month_2 int,
                      |month_3 int,
                      |month_4 int,
                      |month_5 int,
                      |month_6 int,
                      |month_7 int,
                      |month_8 int,
                      |month_9 int,
                      |month_10 int,
                      |month_11 int,
                      |month_12 int,
                      |primary key (product_id, year)
                      |);
                    """.stripMargin
    val preparedStatement = connection.createStatement()
    preparedStatement.execute(MessageFormat.format(query, tableName))
  }

  override def dropTable(connection: Connection, tableName: String): Unit = {
    val query =   """
                  |drop table {0};
                  """.stripMargin
    val preparedStatement = connection.createStatement()
    preparedStatement.execute(MessageFormat.format(query, tableName))
  }

  def readPurchases(resultSet:ResultSet):Array[Int] = {
    val purchases:Array[Int] = new Array[Int](12)
    for(element <- 0 to 11){
      purchases(element) = resultSet.getInt(element+4)
    }
    purchases
  }

  override def loadDataFromDB2OnCloud(connection: Connection, tableName: String): Array[TableRow] = {
    val rows = new Array[TableRow](20000)
    var currentPosition = 0
    val query = "select * from " + tableName + ";"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    val resultSet = preparedStatement.executeQuery()
    while (resultSet.next) {
      val product_id = resultSet.getInt(1)
      val product_group = resultSet.getInt(2)
      val year = resultSet.getInt(3)
      val monthly_purchases = readPurchases(resultSet)
      rows(currentPosition) = new TableRow(product_id, product_group, year, monthly_purchases)
      currentPosition+=1
    }
    rows
  }

  override def getDataFrameFromDB2OnCloud(): DataFrame = {
    val spark = SparkConf().getSparkSession()
    val conf = spark.conf
    val df = spark.read.format(spark.conf.get("driver_type")).
      option("url", conf.get("url")).
      option("dbtable",conf.get("dbtable")).
      option("driver", conf.get("driver")).
      option("lowerBound", 2015).
      option("upperBound",2018).
      option("partitionColumn","YEAR").
      option("numPartitions", 10).
      load()
    df
  }

  override def saveCSVToCOS(df:DataFrame, bucket:String, fileName:String): Unit = {
    val spark = SparkConf().getSparkSession()
    spark.sparkContext.hadoopConfiguration.set("fs.stocator.scheme.list", "cos")
    spark.sparkContext.hadoopConfiguration.set("fs.cos.impl", "com.ibm.stocator.fs.ObjectStoreFileSystem")
    spark.sparkContext.hadoopConfiguration.set("fs.stocator.cos.impl", "com.ibm.stocator.fs.cos.COSAPIClient")
    spark.sparkContext.hadoopConfiguration.set("fs.stocator.cos.scheme", "cos")
    spark.sparkContext.hadoopConfiguration.set("fs.cos.service.access.key", spark.conf.get("spark.access.key"))
    spark.sparkContext.hadoopConfiguration.set("fs.cos.service.secret.key", spark.conf.get("spark.secret.key"))
    spark.sparkContext.hadoopConfiguration.set("fs.cos.service.endpoint", spark.conf.get("spark.endpoint"))

    df.write.mode(SaveMode.Overwrite).format("csv").save("cos://" + bucket + ".service/" + fileName)
  }

  override def saveToCSV(df: DataFrame, path: String): Unit = {
    df.write.option("header",true)
      .mode(SaveMode.Overwrite)
      .partitionBy("YEAR")
      .csv(path)
  }
}
