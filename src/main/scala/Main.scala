import load.DataLoader
import load.impl.DataLoaderImpl
import org.apache.spark.sql.DataFrame
import transform.DataTransformer
import transform.impl.DataTransformerImpl

case object Main {

  /**
   * This is the entry point of the program.
   */
  def main(args: Array[String]): Unit = {

    val loader: DataLoader = new DataLoaderImpl
    val transformer: DataTransformer = new DataTransformerImpl
    val df = loader.getDataFrameFromDB2OnCloud() //extract data
    var transformedDF:Option[DataFrame] = None
    df match {
      case Some(df) => transformedDF = transformer.calcTotalPurchases(df) //transform data
      case None => System.exit(1)
    }
    transformedDF match {
      case Some(transformedDF) => loader.saveCSVToCOS(transformedDF) //load data
      case None => System.exit(1)
    }
    println("Job completed.")
  }
  //loader.saveToCSV(transformedDF, "C:\\Users\\User\\Desktop\\untitled5\\transformedDF\\")
}
