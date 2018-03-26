package com.joedianreid.n26.services;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.joedianreid.n26.models.Statistic;
import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.validators.TransactionValidators;

@Service
public class StatisticService {

	/**
	 * using concurrent queue to prevent concurrency issues, Queue will keep
	 * transactions sorted in descending order by time based of custom
	 * implementation of comparator on the Transaction.compare method
	 */
	private Queue<Transaction> transactionQueue = new PriorityQueue<Transaction>(); // could
																					// have
																					// also
																					// used
																					// a
																					// concurrent
																					// queue

	/**
	 * This statistic will be kept current by the StatisticsGenerator job
	 */
	private Statistic currentStatistic = null;

	/**
	 * Method to return a copy of the statistic, correct to one second This
	 * method returns a copy of the data and not the actual object to make it
	 * safe from modification
	 * 
	 * @return {@link Statistic}
	 */
	public Statistic getCurrentStatistic() {
		Statistic copyOfStatistic = new Statistic();
		if (currentStatistic != null) {
			copyOfStatistic.setAvg(currentStatistic.getAvg());
			copyOfStatistic.setCount(currentStatistic.getCount());
			copyOfStatistic.setMax(currentStatistic.getMax());
			copyOfStatistic.setMin(currentStatistic.getMin());
			copyOfStatistic.setSum(currentStatistic.getSum());
		}
		printQueue();// comment out when not testing
		return copyOfStatistic;
	}

	/**
	 * Add transaction to QUEUE with the newest always at the top time was
	 * already validated on controller
	 * 
	 * @param amount
	 * @return
	 */
	public synchronized boolean addToQue(Transaction transaction) {

		boolean success = false;

		if (transactionQueue.isEmpty()) {

			transactionQueue = new PriorityQueue<>();
		}
		success = true;
		transactionQueue.add(transaction);// add the new value

		return success;
	}

	/**
	 * Update Statistic inside synchronised block to prevent concurrent update
	 * of queue
	 * 
	 * @param newTransactionAmount
	 * @param removedTransactionAmount
	 */
	public void updateStatistics() {

		Statistic statistic = new Statistic();

		int validTransactionCount = 0;

		List<Transaction> last60SecondsTransactions = null;

		synchronized (transactionQueue) {

			// filter queue by collection only transactions with the range of
			// the last 60 seconds
			// range now to now - 60000
			last60SecondsTransactions = transactionQueue.stream()
					.filter(t -> (t.getTimestamp() >= ( Instant.now().atOffset(ZoneOffset.UTC ).toInstant().toEpochMilli() - 60000)))
					.collect(Collectors.toList());

			// update the transaction queue by removing old values
			transactionQueue = new PriorityQueue(last60SecondsTransactions);

			for (Object obj : last60SecondsTransactions) {

				Transaction transaction = (Transaction) obj;
				// ensure only valid timestamp are added
				if (!TransactionValidators.isInvalidTimestamp(transaction.getTimestamp())) {
					validTransactionCount++;// if this value is valid then
											// increment

					statistic.setSum(statistic.getSum() + transaction.getAmount());

					if (transaction.getAmount() > statistic.getMax()) {
						statistic.setMax(transaction.getAmount());
					}

					if (transaction.getAmount() < statistic.getMin()) {
						statistic.setMin(transaction.getAmount());
					}

				}

			}
			// calculate average
			if (validTransactionCount > 0) {
				statistic.setAvg(new Double(statistic.getSum() / validTransactionCount));
				statistic.setCount(validTransactionCount);
			}
		}
		currentStatistic = statistic;
	}

	/**
	 * Utility method for printing contents of queue for debugging
	 */
	private void printQueue() {
		int count = 1;
		for (Transaction transaction : transactionQueue) {
			System.out.println(count + " : " + transaction.toString());
			count++;
		}
	}

}
