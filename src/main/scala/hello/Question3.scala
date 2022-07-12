package hello
import statistics.utils.getTransaction
import statistics.utils.Transaction

object calculateStatisticsForEachDay {
  def calcStats(
      ts: List[Transaction],
      day: Int
  ) = {
    // all transactions for date range, grouped by accountID
    val allTransactionsByAccountID = ts
      .filter(trans =>
        trans.transactionDay > day - 5 && trans.transactionDay < day + 1
      )
      .groupBy(_.accountId);

    println("allTransactionsByAccountID", day, allTransactionsByAccountID)

    // "AA" transactions summered by account id
    val aaSums = allTransactionsByAccountID
      .mapValues(_.filter(_.category == "AA"))
      .mapValues(_.map(_.transactionAmount).sum);

    // "CC" transactions summered by account id
    val ccSums = allTransactionsByAccountID
      .mapValues(_.filter(_.category == "CC"))
      .mapValues(_.map(_.transactionAmount).sum);

    // "FF" transactions summered by account id
    val ffSums = allTransactionsByAccountID
      .mapValues(_.filter(_.category == "FF"))
      .mapValues(_.map(_.transactionAmount).sum);

    // sum of all transactions by account id
    val allSums =
      allTransactionsByAccountID.mapValues(_.map(_.transactionAmount).sum);

    // find transaction with max value for each account id
    val maximumTransaction =
      allTransactionsByAccountID.mapValues(_.map(_.transactionAmount).max);

    val allCounts = allTransactionsByAccountID.mapValues(_.length);

    // output is Map(account id -> (aaSum, allAve))
    allTransactionsByAccountID.map { case (id, transactions) =>
      //average transaction amount for account id
      val averageTransaction =
        transactions.map(_.transactionAmount).sum / transactions.length;
      id -> (maximumTransaction(id), averageTransaction, aaSums(id), ccSums(
        id
      ), ffSums(
        id
      ));
    }
  }
  def main(args: Array[String]): Unit = {
    val transactions = getTransaction.transactions;
    val days = transactions.map(_.transactionDay).distinct.sorted

    days.foreach { day =>
      val stats = calcStats(transactions, day).toSeq
      stats.foreach { case (id, (maxim, average, aaSums, ccSums, ffSums)) =>
        println(s"$day $id $maxim $average $aaSums $ccSums $ffSums")
      }
    }
  }
};
