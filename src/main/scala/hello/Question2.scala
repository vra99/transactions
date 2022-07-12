package hello
import statistics.utils.getTransaction

object averageValueOfTransactionPerAccount {
  def main(args: Array[String]): Unit = {
    val transactions = getTransaction.transactions;
    val averageValueOfTransactionPerAccount = transactions
      .groupBy(_.accountId)
      .mapValues(transactions => {
        val totalTransactions = transactions.map(_.transactionAmount).sum;
        val totalTransactionsPerAccount = transactions.size;
        totalTransactions / totalTransactionsPerAccount;
      });

    averageValueOfTransactionPerAccount.toSeq.sortBy(_._1).foreach(println);
  };
};
