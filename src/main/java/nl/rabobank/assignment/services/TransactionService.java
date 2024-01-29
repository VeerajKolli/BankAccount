package nl.rabobank.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.entities.entity.TransactionHistory;
import nl.rabobank.assignment.entities.enums.TransactionStatus;
import nl.rabobank.assignment.entities.enums.TransactionType;
import nl.rabobank.assignment.entities.enums.StatementType;
import nl.rabobank.assignment.exceptions.InsufficientBalanceManagerException;
import nl.rabobank.assignment.repositories.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionService {

    private final ValidationService validationService;
    private final BankAccountService bankAccountService;
    private final TransactionFeeService transactionFeeService;

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionService(ValidationService validationService,BankAccountService bankAccountService,TransactionFeeService transactionFeeService, TransactionHistoryRepository transactionHistoryRepository)
    {
        this.validationService = validationService;
        this.bankAccountService = bankAccountService;
        this.transactionFeeService = transactionFeeService;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public void executeWithdraw(BankAccount bankAccount, BigDecimal amount) {
        TransactionHistory transactionHistory = getTransactionHistory(bankAccount, amount,TransactionType.WITHDRAW,StatementType.WITHDRAW);
        try {

            takeMoney(transactionHistory, bankAccount, amount);

        } catch (InsufficientBalanceManagerException e) {
            transactionHistory.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transactionHistory.setFailingReason(e.getMessage());
            saveTransactionHistory(transactionHistory);
              throw e;

        } finally {
            saveTransactionHistory(transactionHistory);
        }
    }

    public void executeDeposit(BankAccount bankAccount, BigDecimal amount) {


        TransactionHistory transactionHistory = getTransactionHistory(bankAccount, amount,TransactionType.DEPOSIT,StatementType.DEPOSIT);
        try {
            putMoney(transactionHistory, bankAccount, amount);
        } catch (InsufficientBalanceManagerException e) {
            transactionHistory.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transactionHistory.setFailingReason(e.getMessage());
            saveTransactionHistory(transactionHistory);
            throw e;

        } finally {
            saveTransactionHistory(transactionHistory);
        }
    }

    private TransactionHistory getTransactionHistory(BankAccount bankAccount, BigDecimal amount,TransactionType type,StatementType statementType) {
        TransactionHistory transactionHistory =  new TransactionHistory();
        transactionHistory.setType(type);
        transactionHistory.setStatementType(statementType);
        transactionHistory.setBankAccountId(bankAccount.getId());
        transactionHistory.setCardId(bankAccount.getCard().getId());
        transactionHistory.setCustomerId(bankAccount.getCustomer().getId());
        transactionHistory.setBeforeBalance(bankAccount.getCurrentBalance());
        transactionHistory.setAmount(amount);
        return transactionHistory;
    }


    public void executeTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, final BigDecimal amount) {

        // create TransactionHistoryBuilder for fromBankAccount
        TransactionHistory fromTransactionHistory = new TransactionHistory();
        fromTransactionHistory.setType(TransactionType.TRANSFER);
        fromTransactionHistory.setStatementType(StatementType.WITHDRAW);
        fromTransactionHistory.setBankAccountId(fromBankAccount.getId());
        fromTransactionHistory.setCustomerId(fromBankAccount.getCustomer().getId());
        fromTransactionHistory.setCardId(fromBankAccount.getCard().getId());
        fromTransactionHistory.setBeforeBalance(fromBankAccount.getCurrentBalance());


        TransactionHistory toTransactionHistory = new TransactionHistory();
        toTransactionHistory.setType(TransactionType.TRANSFER);
        toTransactionHistory.setStatementType(StatementType.DEPOSIT);
        toTransactionHistory.setBankAccountId(fromBankAccount.getId());
        toTransactionHistory.setCustomerId(fromBankAccount.getCustomer().getId());
        toTransactionHistory.setCardId(fromBankAccount.getCard().getId());
        try {

            takeMoney(fromTransactionHistory, fromBankAccount, amount);
            saveTransactionHistory(fromTransactionHistory);
            putMoney(toTransactionHistory, toBankAccount, amount);
            saveTransactionHistory(toTransactionHistory);

        } catch (InsufficientBalanceManagerException e) {
            fromTransactionHistory.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            fromTransactionHistory.setFailingReason(e.getMessage());
            saveTransactionHistory(fromTransactionHistory);
            throw e;

        }
    }

    private void takeMoney(TransactionHistory transactionHistory, BankAccount bankAccount, BigDecimal amount) {
        BigDecimal fee = transactionFeeService.getFee(TransactionType.WITHDRAW, bankAccount, amount);
        BigDecimal totalAmount = transactionFeeService.getTotalAmount(amount, fee);

        validationService.checkWithdrawable(bankAccount, totalAmount);

        BankAccount updatedBankAccount = bankAccountService.decreaseCurrentBalance(bankAccount, totalAmount);
        validationService.validateCurrentBalance(updatedBankAccount);

        transactionHistory.setStatus(TransactionStatus.SUCCESS);
        transactionHistory.setFee(fee);
        transactionHistory.setTotalAmount(totalAmount);
        transactionHistory.setAfterBalance(updatedBankAccount.getCurrentBalance());
    }

    private void putMoney(TransactionHistory transactionHistory, BankAccount bankAccount, BigDecimal amount) {

        BankAccount updatedBankAccount = bankAccountService.increaseCurrentBalance(bankAccount, amount);

        transactionHistory.setStatus(TransactionStatus.SUCCESS);
        transactionHistory.setFee(BigDecimal.ZERO);
        transactionHistory.setTotalAmount(amount);
        transactionHistory.setAfterBalance(updatedBankAccount.getCurrentBalance());
        }

    private void saveTransactionHistory(TransactionHistory transactionHistory){
        transactionHistoryRepository.save(transactionHistory);
    }
}
