package statistics.utils
import scala.io.Source

case class Transaction(
    transactionId: String,
    accountId: String,
    transactionDay: Int,
    category: String,
    transactionAmount: Double
)

object getTransaction {
  val fileName = "src/main/resources/transactions.txt"
  val transactionslines = Source.fromFile(fileName).getLines().drop(1)
  //Here we split each line up by commas and construct Transactions
  val transactions: List[Transaction] = transactionslines.map { line =>
    val split = line.split(',')
    Transaction(split(0), split(1), split(2).toInt, split(3), split(4).toDouble)
  }.toList
}
