package connection

object Properties {

    /**
     * This file is used to configure DB2 connection properties.
     */

    val DRIVER = "com.ibm.db2.jcc.DB2Driver"
    val DRIVER_TYPE = "jdbc"
    val TABLE = "..."
    private val URL = "jdbc:db2://..."
    private val DATABASE = "..."
    private val USER = "..."
    private val PASSWORD = "..."

    val CONNECTION_URL = URL + DATABASE + ":" + "user=" + USER + ";password=" + PASSWORD + ';' + "sslConnection=true;"

}
