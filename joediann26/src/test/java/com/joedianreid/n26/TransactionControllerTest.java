package com.joedianreid.n26;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joedianreid.n26.controller.TransactionController;
import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.services.TransactionService;

@RunWith(SpringRunner.class)
public class TransactionControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
    @Autowired 
    private ObjectMapper objectMapper;    
    
    @Autowired
	private TransactionService transactionService;
    
    private JacksonTester <Transaction> jsonTester;
    
    private Transaction transaction;
    
    Instant instant = Instant.now();
    
    long timeStampMillis = instant.toEpochMilli();
    
    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
        transaction = new Transaction();
    }
    
    @Test
    public void test_201() throws Exception {
    	transaction.setAmount(200D);
    	transaction.setTimestamp(timeStampMillis - 1000);
        final String trans = jsonTester.write(transaction).getJson();
        
        mockMvc
            .perform(post("/transactions").content(trans).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()).andDo(document("index"));;
       
    }
    
    @Test
    public void test_204() throws Exception {
    	transaction.setAmount(200D);
    	transaction.setTimestamp(1L);
        final String trans = jsonTester.write(transaction).getJson();
        
        mockMvc
            .perform(post("/transactions").content(trans).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
       
    }
    
    @Test
    public void test_406() throws Exception {

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().is4xxClientError());
    }

	
}
