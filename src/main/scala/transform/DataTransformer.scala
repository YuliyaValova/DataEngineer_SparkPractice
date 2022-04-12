package transform

import entity.TableRow
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame

trait DataTransformer {

  def calcTotalPurchases(df:DataFrame):DataFrame

}
