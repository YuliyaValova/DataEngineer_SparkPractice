package load

import org.apache.spark.sql.DataFrame


trait DataLoader {

  /**
   * This method is used to load data from DB2 on Cloud into the DataFrame.
   * @return Option[DataFrame] - Downloaded DataFrame or None if smth going wrong.
   */
  def getDataFrameFromDB2OnCloud():Option[DataFrame]

  /**
   * This method is used to save data in Cloud Object Storage in the CSV format.
   * @param df - DataFrame for saving.
   */
  def saveCSVToCOS(df:DataFrame):Unit

  /**
   * This method is used to save data locally in the CSV format.
   * @param df - DataFrame for saving.
   * @param path - Path to save to computer.
   */
  def saveToCSV (df:DataFrame, path:String)
}
