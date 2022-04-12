package load

import entity.TableRow
import org.apache.spark.sql.DataFrame

import java.sql.Connection


trait DataLoader {

  def createTable(connection: Connection, tableName:String):Unit

  def dropTable(connection: Connection, tableName:String):Unit

  def addTableRecord(tableName:String, connection: Connection, row:TableRow):Boolean

  def loadDataFromDB2OnCloud(connection: Connection,tableName:String):Array[TableRow]

  def getDataFrameFromDB2OnCloud():DataFrame

  def saveCSVToCOS(df:DataFrame, bucket:String, fileName:String):Unit

  def saveToCSV (df:DataFrame, path:String)
}
