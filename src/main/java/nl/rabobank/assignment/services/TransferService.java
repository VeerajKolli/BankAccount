package nl.rabobank.assignment.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Transfer process management service
 */
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class TransferService {

    private TransactionService transactionService;
    private BankAccountService bankAccountService;

    public void transfer(Long fromBankAccountId, Long toBankAccountId, BigDecimal amount) {
        BankAccount fromBankAccount = bankAccountService.getBankAccount(fromBankAccountId);
        BankAccount toBankAccount = bankAccountService.getBankAccount(toBankAccountId);

        transactionService.executeTransfer(fromBankAccount, toBankAccount, amount);
    }


}
