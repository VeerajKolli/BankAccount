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
URL: http://localhost:8080/api/customer/1
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
URL:http://localhost:8080/api/bank/account/1

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

# 6. Get account balance for a particular customerId
Method:GET 
URL: http://localhost:8080/api/bank/account/balance/9
Request Body :{}
Response Body :
{
    "bankAccountId": 9,
    "currentBalance": 1000.00
}

# 7. Withdraw money from CustomerId

Method : Post
URL: http://localhost:8080/api/depositorwithdraw/withdraw/10
Request Body :{"amount":"100.0"}
Response : 200 Ok

# 8. Withdraw money from CustomerId

Method : Post
URL: http://localhost:8080/api/depositorwithdraw/deposit/10
Request Body :{"amount":"100.0"}
Response : 200 Ok

# 9. Transfer money between Customers

Method : Post
URL: http://localhost:8080/api/transfer/10/11
Request Body :{"amount":"100.0"}
Response : 200 Ok
