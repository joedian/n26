/**
 * 
 */
package com.joedianreid.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.models.TransactionResponse;
import com.joedianreid.n26.services.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public TransactionResponse createTransaction(@RequestBody Transaction transaction){
				
		boolean transactionAdded = transactionService.addTransaction(transaction);
		
		if(transactionAdded)
			return new TransactionResponse(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value());
		   
	    return new TransactionResponse(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT.value());
	 }

}
