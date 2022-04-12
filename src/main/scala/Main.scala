import load.DataLoader
import load.impl.DataLoaderImpl
import transform.DataTransformer
import transform.impl.DataTransformerImpl

case object Main {

  def main(args: Array[String]): Unit = {

    val loader: DataLoader = new DataLoaderImpl
    val transformer: DataTransformer = new DataTransformerImpl
    var time = System.currentTimeMillis
    val df = loader.getDataFrameFromDB2OnCloud()
    println("AFTER LOADING: " + (System.currentTimeMillis - time))
    time = System.currentTimeMillis
    val transformedDF = transformer.calcTotalPurchases(df)
    println("AFTER TRANSFORMING: " + (System.currentTimeMillis - time))
    time = System.currentTimeMillis
    loader.saveCSVToCOS(transformedDF, "storage-test-lulka", "data1.csv")
    println("AFTER SAVING: " +(System.currentTimeMillis - time))
    println("Job completed successfully!")
    //loader.saveToCSV(transformedDF, "C:\\Users\\User\\Desktop\\DataEngineer_SparkPractice\\transformedDF\\")
  }
}
