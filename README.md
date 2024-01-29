# Read Me First
The following was discovered as part of building this project:

# Getting Started

This application is a simple bank account implementation. An account able to withdraw money and transfer money to another account. The transfer can be with debit card or credit card. If a credit card is used, extra %1 transaction fee is charged. An account can be linked with a credit card or bank account.

# Requirements and Assumptions

1. Mysql database is used to implement this project and after downloading, create a database schema and pass the URL,username and password in application.properties under src/main/resources. Attached scripts are located in the project directory(Database_mysql.zip). This scripts must be implemented before starting springboot application.
2. ![image](https://github.com/VeerajKolli/BankAccount/assets/79200457/2d1e2e1b-b79f-4257-b25c-52cd30e070cb) has been added as external library to the project as the dependency was not found in rabobank-public repository.

    Postman requests responses and URL can be found here : RestApis.md in project directory.  
3. It should be possible to transfer and withdraw money from an account. It is possible to pay with either debit card or credit card. If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra. Use design patterns where applicable and write some test cases as well.  
    TransferControllerIT and WithdrawControllerIT is created to test the requirement. And also fee amount totalAmount beforeBalance afterBalance fields are visible to see the amount changes by a transaction in transaction_history.  
4. A negative balance is not possible.  
    InsufficientBalanceException created to manage negative or possible negative balances.  
5. Account should contain at least some user details, card details and current balance.  
    All models are placed in nl.rabobank.assignment.entities package.  
6. One rest endpoint to see current available balance in all accounts
    BankAccountController.getAllBalances and BankAccountController.getBalance.  
7. One rest endpoint to Deposit money  
      DepositWithdrawController.deposit  
8. One rest endpoint to withdraw money  
    WithdrawController.withdraw  
9. One rest endpoint to transfer money  
    TransferController.transfer
10. One credit card or debit card is linked with one account  
    See the @OneToOne relation on database.  
11. It should be able to audit transfers or withdrawals  
    updatedAt and createdAt fields placed in all tables and transaction_history table is created to keep all changes.  

# Testing

1. 3 customers are already part of the scripts which can be used for testing.  
2. Create bank account at-least for 2 customers using rest api to test transfers, withdraw and deposits.
3. Remaining rest api's should be able to access without any issues