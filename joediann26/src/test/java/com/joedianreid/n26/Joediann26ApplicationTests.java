package com.joedianreid.n26;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.joedianreid.n26.controller.StatisticsController;
import com.joedianreid.n26.controller.TransactionController;
import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.models.TransactionResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(methodMode=MethodMode.BEFORE_METHOD)
public class Joediann26ApplicationTests {
	
	Instant instant = Instant.now();
    
    Long timeNow = instant.toEpochMilli();
	
	private Long minTimeAllowed = timeNow - 60000;
	
	private int numberOfTestCalls = 62;
	
	private int intervalOfCalls = 100;
	
	@Autowired
	TransactionController transactionController;
	
	@Autowired
	StatisticsController statisticsController;
	

	@Test
	public void contextLoads() {
		assertThat(transactionController).isNotNull();
	}
		
	
	@Test
    public void test_201() throws Exception {      
			
		TransactionResponse response = transactionController.createTransaction(new Transaction(20D, timeNow -1000));			
				
		Thread.sleep(1000);			
		
		assertEquals(response.getResponseCode(), 201);
		
    }

    @Test
    public void test_204_future() throws Exception {      
    	TransactionResponse response = transactionController.createTransaction(new Transaction(20D, timeNow + 40000));			
		
		Thread.sleep(1000);			
		
		assertEquals(response.getResponseCode(), 204);
    }
    
    
    @Test
    public void test_204_past() throws Exception {      
    	TransactionResponse response = transactionController.createTransaction(new Transaction(20D, timeNow - 200000));			
		
		Thread.sleep(1000);			
		
		assertEquals(response.getResponseCode(), 204);
    }

}
