package com.joedianreid.n26.scheduler;

import java.time.Instant;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joedianreid.n26.models.Statistic;
import com.joedianreid.n26.models.Transaction;
import com.joedianreid.n26.services.StatisticService;

@Component
public class StatisticsGenerator {
	
	@Autowired
	StatisticService statisticService;

	public StatisticsGenerator() {
	}
	
	private static final Logger log = LoggerFactory.getLogger(StatisticsGenerator.class);

    @Scheduled(fixedRate = 5000)//run it every five seconds
    public void generateCurrentStatistics() {
    	 System.out.println("Current timestamp: " +  Instant.now().atOffset(ZoneOffset.UTC ).toInstant().toEpochMilli());
        statisticService.updateStatistics();
        Statistic stat = statisticService.getCurrentStatistic();
        //log.info("The statistics are now",stat.toString() );
        System.out.println(stat.toString());
    };
    
    
    /**
     * Method commented by default, please uncomment to use for testing
     * 1. create transaction
     * 2. add transaction
     * 3. custom intervals
     */
    @Scheduled(fixedRate = 1000)//run it every second
    public void generateTransactions() {
    	Transaction transaction = new Transaction();
    	Double rand = Math.random();
    	transaction.setAmount(Math.floor(5D * rand * 10)); //random number generation
    	transaction.setTimestamp( Instant.now().atOffset(ZoneOffset.UTC ).toInstant().toEpochMilli());
        statisticService.addToQue(transaction);
    }

}
