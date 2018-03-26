package com.joedianreid.n26.validators;
import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.joedianreid.n26.exceptions.InvalidTransactionException;
import com.joedianreid.n26.models.Transaction;

@Service
public class TransactionValidators {
	
	private static final String INVALID_TRANSACTION = "Transaction is invalid or corrupted.";
	
	private static final String INVALID_AMOUNT = "Transaction Amount is invalid.";
	
	private static final String INVALID_TIMESTAMP = "Transaction Timestamp is invalid. Time is either too old or in the future.";

	/**
	 * max age of transactions to be in statistic
	 */
	private static Long  MAX_MILLISECONDS_HELD = 60000L;

	public TransactionValidators() {
		
	}
	
	/**
	 * 
	 * 
	 * @param transaction
	 * @throws Exception
	 */
	public void validateTransaction(Transaction transaction) throws InvalidTransactionException{
		if(transaction == null)
			throw new InvalidTransactionException(INVALID_TRANSACTION);
		
		if(transaction.getAmount() == null)
			throw new InvalidTransactionException(INVALID_AMOUNT);
		
		
		if(transaction.getTimestamp() == null || isInvalidTimestamp(transaction.getTimestamp()))
			throw new InvalidTransactionException(INVALID_TIMESTAMP);
		
	}
	
	
	/**
	 * Utility method to check if the new timestamp is less than 60 seconds old, or in the future
	 * . In both cases is invalid, otherwise is valid.
	 * 
	 * @param transactionTime
	 * @return
	 */
	public static boolean isInvalidTimestamp(Long transactionTime){
		long currentTimeInMilliseconds =  Instant.now().atOffset(ZoneOffset.UTC ).toInstant().toEpochMilli();
		
		Long oldestTimeAccepted = currentTimeInMilliseconds - MAX_MILLISECONDS_HELD;
		
		if( transactionTime < oldestTimeAccepted || transactionTime > currentTimeInMilliseconds)
			return true;
		
		return false;
	}


}
