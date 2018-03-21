# 2dayChallenge

Actual code in the joediann26 folder

Purpose of project:
Provide two webservices with two endpoints to :
- allow transactions to be added to a state variable that stores all the transactions that are less than 60 seconds old
- provide endpoint for statistics of such values


#achieved
- working endpoints
- tests

#notachieved due to time constraints (day job and other activities, could not do task last weekend) probably shoudl have chosen nexrt weekend :-)

- O(n) for statistics retrieval
Approach to be taken if calculating statistic eacn time a transaction is added which gives O(1) time for retrieval however this creates a o(n) insertion for each new Transaction to data store. Using a priority quee ordered by most recent time as I have done will ensire for a queue q, getting statistics can be 0(q - oldTransactions) as the old transactions are always at the bottom and can be ignored when doing statistics.

A space tradeoff could be recreating the queue each time the transaction is added, ensureing it always have 'fresh records'


#TO RUN PROJECT
- check out project
- mvn clean install
- run spring boot application class

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
      
      
    
