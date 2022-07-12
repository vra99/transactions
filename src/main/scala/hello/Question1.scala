package hello
import statistics.utils.getTransaction

object getTotalTransactionsForEachDay {
  def main(args: Array[String]): Unit = {
    val transactions = getTransaction.transactions;
    val totalTransactionsForEachDay = transactions
      .groupBy(_.transactionDay)
      .mapValues(transactions => transactions.map(_.transactionAmount).sum);
    totalTransactionsForEachDay.toSeq.sortBy(_._1).foreach(println);
  };
};
