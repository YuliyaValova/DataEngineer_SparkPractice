package connection

object Properties {

    //db2 properties
    val DRIVER = "com.ibm.db2.jcc.DB2Driver"
    val DRIVER_TYPE = "jdbc"
    val TABLE = "Sales_test"
    private val URL = "..."
    private val DATABASE = "..."
    private val USER = "..."
    private val PASSWORD = "..."
    val CONNECTION_URL = URL + DATABASE + ":" + "user=" + USER + ";password=" + PASSWORD + ';' + "sslConnection=true;"

}
