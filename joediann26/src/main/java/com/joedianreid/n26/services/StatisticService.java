package com.joedianreid.n26.services;

import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Service;

import com.joedianreid.n26.models.Statistic;
import com.joedianreid.n26.models.Transaction;

@Service
public class StatisticService {

	/**
	 * using concurrent queue to prevent concurrency issues, 
	 * Queue will keep transactions sorted in descending order by time
	 */
	private Queue<Transaction> transactionQueue = new PriorityBlockingQueue<>();
	
	/**
	 * max age of transactions to be in statistic
	 */
	private static Long  MAX_MILLISECONDS_HELD = 60000L;
	
	
	/**
	 * Add transaction to QUEUE with the newest always at the top
	 * 
	 * @param amount
	 * @return
	 */
	public synchronized boolean addToQue(Transaction transaction) {
		
		boolean success = false;
		
		synchronized(transactionQueue){

			if (transactionQueue.isEmpty()) {
				
				transactionQueue = new ConcurrentLinkedQueue<>();				
			} 
			success = true;
			transactionQueue.add(transaction);// add the new value				
		}
		return success;
	}


	/**
	 * Update Statistic inside synchronised block to prevent concurrent update of queue
	 * 
	 * @param newTransactionAmount
	 * @param removedTransactionAmount
	 */
	public Statistic getStatistics() {
		
		Statistic statistic = new Statistic();
		
		int validTransactionCount = 0;
		
		synchronized (transactionQueue) {
			
			for(Transaction transaction: transactionQueue){
				
				//ensure only valid timestamps are added
				if(!isInvalidTimestamp(transaction.getTimestamp())){
					validTransactionCount++;// if this value is valid then increment
					
					statistic.setSum(statistic.getSum() + transaction.getAmount());
					
					if(transaction.getAmount() > statistic.getMax()){
						statistic.setMax(transaction.getAmount());
					}
					
					if(transaction.getAmount() < statistic.getMin()){
						statistic.setMin(transaction.getAmount());
					}					
								
			  }else{
				  //reached invalid values in queue
				  break;
			  }
				
				if(validTransactionCount > 0){
					statistic.setAvg(new Double(statistic.getSum() / validTransactionCount));
					
					statistic.setCount(validTransactionCount);		
				}
			}
			
						
		}
		printQueue();
		System.out.println(statistic.toString());
		return statistic;
	}
	
	
	/**
	 * Utility method for printing contents of queue for debugging
	 */
	private void printQueue(){
		int count = 1;
		for(Transaction transaction: transactionQueue){
			System.out.println(count + " : " + transaction.toString());
			count++;
		}
	}
	
	/**
	 * Utility method to check if the new timestamp is less than 60 seconds old, or in the future
	 * . In both cases is invalid, otherwise is valid.
	 * 
	 * @param transactionTime
	 * @return
	 */
	public static boolean isInvalidTimestamp(Long transactionTime){
		Instant instant = Instant.now();
		
		long currentTimeInMilliseconds = instant.toEpochMilli();
		
		Long oldestTimeAccepted = currentTimeInMilliseconds - MAX_MILLISECONDS_HELD;
		
		if( transactionTime < oldestTimeAccepted || transactionTime > currentTimeInMilliseconds)
			return true;
		
		return false;
	}


}
