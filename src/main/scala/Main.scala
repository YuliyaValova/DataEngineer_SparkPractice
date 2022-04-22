import load.DataLoader
import org.apache.spark.sql.DataFrame
import transform.DataTransformer

case object Main {

  /**
   * This is the entry point of the program.
   */
  def main(args: Array[String]): Unit = {
    val loader = new DataLoader
    val transformer = new DataTransformer
    val df = loader.getData()//extract data
    var transformedDF:Option[DataFrame] = None
    df match {
      case Some(df) => transformedDF = transformer.calcTotalPurchases(df) //transform data
      case None => {
        println("Data loading step is failed.")
        System.exit(1)
      }
    }
    transformedDF match {
      case Some(transformedDF) => loader.saveCSV(transformedDF) //load data
      case None => {
        println("Reading data transforming step failed.")
        System.exit(1)
      }
    }
    println("Job completed.")
  }
}
