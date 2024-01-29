# 1. Create new customers 
Method: PUT   
URL: http://localhost:8080/api/customer  
Request Body  
{  
        "firstName":"Veeraj",  
        "lastName": "Kolli",  
        "initial":"V",  
        "email": "spiderman@test.nl",  
        "phoneNumber":"012345678"  
}
Response: Http 200 Ok  

# 2. Get all customers  
Method : GET    
URL: http://localhost:8080/api/customer/all  
Request Body: {}  
Response Body:  
    {  
        "firstName": "Veeraj",  
        "lastName": "Kolli",  
        "initial": "V",  
        "email": "spiderman@test.nl",  
        "phoneNumber": "012345678"  
    }  
# 3. Get a particular customer using customerID   
Method: GET   
URL: http://localhost:8080/api/customer/{customerId}   
Request Body : {}  
Response Body:   
{  
    "firstName": "Veeraj",  
    "lastName": "Kolli",  
    "initial": "V",  
    "email": "spiderman@test.nl",  
    "phoneNumber": "012345678"  
}  

# 4. Create new bank account for customerID  
Method: PUT   
URL:http://localhost:8080/api/bank/account/{customerID}
Request Body:  
{  
    "iban":"NL00XXXX01234567",  
    "balance": "1000.0",  
    "card":{  
        "cardType":"DEBIT_CARD",  
        "number":"1111222233334444",  
        "holderName":"Veeraj",  
        "expiryDate":"01/26",  
        "cvv":"123"  
    }  
}  

Response Body : Http 200 Ok  

# 5. get account balance for all customers  
Method: GET   
URL: http://localhost:8080/api/bank/account/balance/all  
Request Body :{}  
Response Body :  
    {  
        "bankAccountId": 9,  
        "currentBalance": 1000.00  
    }  

# 6. Get account balance for a particular Account Id  
Method:GET   
URL: http://localhost:8080/api/bank/account/balance/{AccountId}   
Request Body :{}  
Response Body :  
{  
    "bankAccountId": 9,  
    "currentBalance": 1000.00  
}  

# 7. Withdraw money from Account ID  

Method : Post  
URL: http://localhost:8080/api/depositorwithdraw/withdraw/{AccountId}  
Request Body :{"amount":"100.0"}  
Response : 200 Ok  

# 8. Withdraw money fromAccount ID  
  
Method : Post  
URL: http://localhost:8080/api/depositorwithdraw/deposit/{AccountId}   
Request Body :{"amount":"100.0"}  
Response : 200 Ok  
  
# 9. Transfer money between Customers using Account IDs
  
Method : Post  
URL: http://localhost:8080/api/transfer/{AccountId1}/{AccountId2} 
Request Body :{"amount":"100.0"}  
Response : 200 Ok
