package transform.impl

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import transform.DataTransformer

case class DataTransformerImpl() extends DataTransformer {

  override def calcTotalPurchases(df: DataFrame): Option[DataFrame] = {
      try {
        val dfWithTotal = df.withColumn("year_purchases",
          col("MONTH_1") + col("MONTH_2") +
            col("MONTH_3") + col("MONTH_4") +
            col("MONTH_5") + col("MONTH_6") +
            col("MONTH_7") + col("MONTH_8") +
            col("MONTH_9") + col("MONTH_10") +
            col("MONTH_11") + col("MONTH_12")).drop("MONTH_1", "MONTH_2", "MONTH_3", "MONTH_4", "MONTH_5", "MONTH_6", "MONTH_7", "MONTH_8", "MONTH_9", "MONTH_10", "MONTH_11", "MONTH_12")
        Some(dfWithTotal)
      } catch {
        case e:Exception => {
          println("ERROR: Data transforming failed.")
          None
        }
      }

    //This is a different way, but slower

    /*val sumColumns:Array[Column] = df.columns.takeRight(12).map(colName => col(colName))
    var newDf = df.withColumn("Total", sumColumns.reduce(_+_))
    sumColumns.foreach(col => newDf = newDf.drop(col))
    newDf*/
  }
}
