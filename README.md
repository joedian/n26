# 2dayChallenge

Actual code in the joediann26 folder

Purpose of project:
Provide two webservices with two endpoints to :
- allow transactions to be added to a Queue that stores all the transactions that are less than 60 seconds old
- provide endpoint for statistics of such values


#achieved
- working endpoints
- tests
- getstatistics method is in constant O(n) time as it is allways current to the second due to the scheduler


- O(n) for statistics retrieval
Approach to be taken for calculating statistic, doing it each second using a scheduler. This gives O(1) time for retrieval for statistics as it is precalculated. Calculating the statistics itself creates a o(n) calculation, where n is the number of valid transactions in the queue. Using a priority queue ordered by most recent time as I have done will ensure for a queue q, getting statistics can be 0(q - oldTransactions) as the old transactions are always at the bottom and can be ignored when doing statistics. In the end code was optimized to filter old transactions away. in case hostory is every needed, queue does not have to be modified in this way.

A space tradeoff is therefore taken as we are recreating the queue each time the statistics are generated, ensuring it always have 'fresh records'.


#TO RUN PROJECT
- check out project
- mvn clean install
- run spring boot application class (if using IDE right click on project and run as spring boot)

#To TEST
USing Postman App

-transactions , http://localhost:8080/transactions
    - Set the request type to POST
      Set the content type in the header to application/json; charset=UTF-8
      Add the JSON for the Transaction to the body of the request (in the raw option)
      Add the request path
      Press send
- statistics , http://localhost:8080/statistics 
    - Set the request type to GET
      Set the content type in the header to application/json; charset=UTF-8
      Add the request path
      Press send
      
      
   # Scheduler 
   - generateCurrentStatistics
   Method keeps statistics current by purging transactionQueue and keeping stats current 
   
  - generateTransactions
   can be used to test by inserting records every second
      
    
    #To Do
    - better use of properties file
    - implement more tests
