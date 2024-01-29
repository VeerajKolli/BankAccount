# Read Me First
The following was discovered as part of building this project:

# Getting Started

This application is a simple bank account implementation. An account able to deposit, withdraw money and transfer money to another account. The transfer can be with debit card or credit card. If a credit card is used, extra %1 transaction fee is charged.

# Prerequisites
JDK17  
Maven  
Intellij IDE  
Postman  
MySQL Server and Workbench  

# Stacks
Spring Boot  
Hibernate/JPA  
MySQL  
Lombok  
  
# Requirements and Assumptions

1. Mysql database is used to implement this project and after downloading, create a database schema(temp) and pass the URL,username and password in application.properties under src/main/resources. Attached scripts are located in the project directory(mysql_database_scripts.zip). This scripts must be implemented before starting springboot application.
   Please note that scripts must match the database schema which was newly created and scripts might change for different databases.
2. ![image](https://github.com/VeerajKolli/BankAccount/assets/79200457/2d1e2e1b-b79f-4257-b25c-52cd30e070cb) has been added as external library to the project as the dependency was not found in rabobank-public repository.

    Postman examples can be found here : RestApis.md in project directory.  
3. It should be possible to transfer and withdraw money from an account. It is possible to pay with either debit card or credit card. If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra. Use design patterns where applicable and write some test cases as well.  
    fee amount totalAmount beforeBalance afterBalance fields are visible to see the amount changes by a transaction in transaction_history.  
4. A negative balance is not possible.  
    InsufficientBalanceException created to manage negative or possible negative balances.  
5. Account should contain at least some user details, card details and current balance.  
    All models are placed in nl.rabobank.assignment.entities package.  
6. One rest endpoint to see current available balance in all accounts
    BankAccountController.getAllBalances and BankAccountController.getBalance.  
7. One rest endpoint to Deposit money (Additionally added)   
    DepositWithdrawController.deposit  
8. One rest endpoint to withdraw money  
    DepositWithdrawController.withdraw  
9. One rest endpoint to transfer money  
    TransferController.transfer
10. One credit card or debit card is linked with one account  
    See the @OneToOne relation on database.  
11. It should be able to audit transfers or withdrawals  
    updatedAt and createdAt fields placed in all tables and transaction_history table is created to keep all changes.  

# Testing

1. 3 customers with 3 bank accounts with card details are already part of the scripts which can be used for testing.
    ![image](https://github.com/VeerajKolli/BankAccount/assets/79200457/b536135d-83a7-4b80-8c76-e2a795043585)
    ![image](https://github.com/VeerajKolli/BankAccount/assets/79200457/b19be007-358b-4751-83c7-148211b4572d)
    ![image](https://github.com/VeerajKolli/BankAccount/assets/79200457/91ff7ebd-8b36-409c-9d41-8bb4689c9c9f)

#Still to do
1. UnitTests needs to be included for the project.  
2. More validations is required for few fields, hiding database passwords from configuration files.  
