# Read Me First
The following was discovered as part of building this project:

# Getting Started

This application is a simple bank account implementation. An account able to withdraw money and transfer money to another account. The transfer can be with debit card or credit card. If a credit card is used, extra %1 transaction fee is charged. An account can be linked with a credit card or bank account.

It should be possible to transfer and withdraw money from an account. It is possible to pay with either debit card or credit card. If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra. Use design patterns where applicable and write some test cases as well.
TransferControllerIT and WithdrawControllerIT is created to test the requirement. And also fee amount totalAmount beforeBalance afterBalance fields are visible to see the amount changes by a transaction in transaction_history
A negative balance is not possible
InsufficientBalanceException created to manage negative or possible negative balances
Account should contain at least some user details, card details and current balance
All models are placed in nl.rabobank.assignment.entities package
One rest endpoint to see current available balance in all accounts
BankAccountController.getAllBalances and BankAccountController.getBalance
One rest endpoint to withdraw money
WithdrawController.withdraw
One rest endpoint to transfer money
TransferController.transfer
One credit card or debit card is linked with one account
See the @OneToOne relation on database
It should be able to audit transfers or withdrawals
updatedAt and createdAt fields placed in all tables and transaction_history table is created to keep all changes.
