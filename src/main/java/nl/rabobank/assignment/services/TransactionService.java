package nl.rabobank.assignment.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.entities.entity.TransactionHistory;
import nl.rabobank.assignment.entities.enums.TransactionStatus;
import nl.rabobank.assignment.entities.enums.TransactionType;
import nl.rabobank.assignment.entities.enums.StatementType;
import nl.rabobank.assignment.exceptions.InsufficientBalanceManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class TransactionService {

    private static final String ERROR_CREATING_INSERTER = "Error while transaction history to executor";

    private ValidationService validationService;
    private BankAccountService bankAccountService;
    private TransactionFeeService transactionFeeService;
    private ApplicationEventPublisher applicationEventPublisher;

    public void executeWithdraw(BankAccount bankAccount, BigDecimal amount) {

        TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.WITHDRAW,
                StatementType.WITHDRAW,
                bankAccount,
                amount);
        try {

            takeMoney(transactionHistoryBuilder, bankAccount, amount);

        } catch (InsufficientBalanceManagerException e) {
              throw e;

        } finally {
        }
    }


    public void executeTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, final BigDecimal amount) {

        // create TransactionHistoryBuilder for fromBankAccount
        TransactionHistory.TransactionHistoryBuilder fromTransactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.TRANSFER,
                StatementType.WITHDRAW,
                fromBankAccount,
                amount);

        // create TransactionHistoryBuilder for toBankAccount
        TransactionHistory.TransactionHistoryBuilder toTransactionHistoryBuilder = getTransactionHistoryBuilder(
                TransactionType.TRANSFER,
                StatementType.DEPOSIT,
                toBankAccount,
                amount);

        try {

            takeMoney(fromTransactionHistoryBuilder, fromBankAccount, amount);
            putMoney(toTransactionHistoryBuilder, toBankAccount, amount);

        } catch (InsufficientBalanceManagerException e) {
            throw e;

        } finally {
        }
    }

    private void takeMoney(TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder, BankAccount bankAccount, BigDecimal amount) {
        BigDecimal fee = transactionFeeService.getFee(TransactionType.WITHDRAW, bankAccount, amount);
        BigDecimal totalAmount = transactionFeeService.getTotalAmount(amount, fee);

        validationService.checkWithdrawable(bankAccount, totalAmount);

        BankAccount updatedBankAccount = bankAccountService.decreaseCurrentBalance(bankAccount, totalAmount);
        validationService.validateCurrentBalance(updatedBankAccount);

        transactionHistoryBuilder.status(TransactionStatus.SUCCESS)
                .fee(fee)
                .totalAmount(totalAmount)
                .afterBalance(updatedBankAccount.getCurrentBalance());
    }

    private void putMoney(TransactionHistory.TransactionHistoryBuilder transactionHistoryBuilder, BankAccount bankAccount, BigDecimal amount) {

        BankAccount updatedBankAccount = bankAccountService.increaseCurrentBalance(bankAccount, amount);

        transactionHistoryBuilder.status(TransactionStatus.SUCCESS)
                .fee(BigDecimal.ZERO)
                .totalAmount(amount)
                .afterBalance(updatedBankAccount.getCurrentBalance());
    }

    private TransactionHistory.TransactionHistoryBuilder getTransactionHistoryBuilder(
            TransactionType transactionType,
            StatementType statementType,
            BankAccount bankAccount,
            BigDecimal amount) {

        return TransactionHistory.builder()
                .type(transactionType)
                .statementType(statementType)
                .amount(amount)
                .customerId(bankAccount.getCustomer().getId())
                .bankAccountId(bankAccount.getId())
                .cardId(bankAccount.getCard().getId())
                .beforeBalance(bankAccount.getCurrentBalance());
    }
}
