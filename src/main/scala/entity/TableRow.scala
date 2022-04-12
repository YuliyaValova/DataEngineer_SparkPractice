package entity

class TableRow(var product_id:Int, var product_group:Int, var year:Int, var monthly_purchases:Array[Int]) {

  def setProductId(id:Int): Unit ={
    this.product_id = id
  }

  def setProductGroup(group:Byte): Unit ={
    this.product_group = group
  }

  def setYear(year:Int): Unit ={
    this.year = year
  }

  def setPurchases(purchases:Array[Int]): Unit ={
    this.monthly_purchases = purchases
  }


  override def toString = product_id + "," + product_group + "," + year + "," +
    monthly_purchases(0) + "," +
    monthly_purchases(1) + "," +
    monthly_purchases(2) + "," +
    monthly_purchases(3) + "," +
    monthly_purchases(4) + "," +
    monthly_purchases(5) + "," +
    monthly_purchases(6) + "," +
    monthly_purchases(7) + "," +
    monthly_purchases(8) + "," +
    monthly_purchases(9) + "," +
    monthly_purchases(10) + "," +
    monthly_purchases(11)
}
