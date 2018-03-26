package com.joedianreid.n26.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.validators.TransactionValidators;


@Service
public class TransactionService {
	
	@Autowired
	private StatisticService statisticService;
	
	
	/**
	 * Method validates if timeperiod is too old or in the future before adding to queue
	 * 
	 * @param transaction
	 * @return boolean
	 */
	public boolean addTransaction(Transaction transaction){
				
		if(transaction == null || transaction.getTimestamp() == null || 
				TransactionValidators.isInvalidTimestamp(transaction.getTimestamp())){
			return false;
		}
		
		return statisticService.addToQue(transaction);
	}
	
	
}
