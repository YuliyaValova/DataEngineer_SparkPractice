package transform

import org.apache.spark.sql.DataFrame

trait DataTransformer {

  /**
   * This method transform data in such way:
   * For each row calculate total of monthly purchases
   * Save the year total as a new column year_purchases
   * Remove the columns with monthly amounts from the data frame
   * @param df - DataFrame for transforming.
   * @return Option[DataFrame] - Transformed DataFrame or None if smth going wrong.
   */
  def calcTotalPurchases(df:DataFrame):Option[DataFrame]

}
