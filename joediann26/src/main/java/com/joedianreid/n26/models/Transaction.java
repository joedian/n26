package com.joedianreid.n26.models;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction implements Comparator<Transaction>{
	
	private Double amount;
	
	private Long timestamp;

		
	public Transaction(Double amount, Long timestamp) {
		super();
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", timestamp=" + timestamp + "]";
	}

	/**
	 * Will allow timestamps to be in descending order so newest at top in QUEUE
	 */
	@Override
	public int compare(Transaction o1, Transaction o2) {
		
		 int retVal = o2.getTimestamp().compareTo(o1.getTimestamp());
		 
		 return retVal;
	}
	
}
