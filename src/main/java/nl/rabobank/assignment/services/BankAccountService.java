package nl.rabobank.assignment.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.entities.entity.Card;
import nl.rabobank.assignment.entities.entity.Customer;
import nl.rabobank.assignment.repositories.BankAccountRepository;
import nl.rabobank.assignment.repositories.CardRepository;
import nl.rabobank.assignment.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.rabobank.assignment.exceptions.BankAccountManagerException;
import java.math.BigDecimal;
import java.util.List;

/**
 * BankAccount management service
 */
@Service
@Slf4j
public class BankAccountService {

    private static final String MESSAGE_FORMAT_NO_BANK_ACCOUNT = "No bankAccount by bankAccountId: %s";

    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final CustomerService customerService;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository,CardRepository cardRepository,CustomerService customerService){
        this.bankAccountRepository = bankAccountRepository;
        this.cardRepository = cardRepository;
        this.customerService = customerService;

    }

    public BankAccount addBankAccount(Long customerId, BankAccount bankAccount) {
        Customer customer = customerService.getCustomer(customerId);
        bankAccount.setCustomer(customer);

        Card card = bankAccount.getCard();
        card.setHolderName(Utils.getCardHolderName(customer));

        // a workaround
        bankAccount.setCard(null);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        log.info("A bank account saved for customer: {}", customerId);

        card.setBankAccount(savedBankAccount);
        cardRepository.save(card);

        return savedBankAccount;
    }

    public BankAccount getBankAccount(Long bankAccountId) {

        return bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountManagerException(MESSAGE_FORMAT_NO_BANK_ACCOUNT));
    }


    public List<BankAccount> getBankAccountList() {
        return bankAccountRepository.findAll();
    }

    public BankAccount decreaseCurrentBalance(BankAccount bankAccount, BigDecimal amount) {
        int effectedRows = bankAccountRepository.decreaseCurrentBalance(bankAccount.getId(), amount);
        if (effectedRows == 0) {
            throw new BankAccountManagerException(
                    "The bank account is not effected of withdraw");
        }

        return bankAccountRepository.findById(bankAccount.getId())
                .orElseThrow(() -> new BankAccountManagerException (MESSAGE_FORMAT_NO_BANK_ACCOUNT));
    }

    public BankAccount increaseCurrentBalance(BankAccount bankAccount, BigDecimal amount) {
        int effectedRows = bankAccountRepository.increaseCurrentBalance(bankAccount.getId(), amount);
        if (effectedRows == 0) {
            throw new BankAccountManagerException(
                    "The bank account is not effected of transfer");
        }

        return bankAccountRepository.findById(bankAccount.getId())
                .orElseThrow(() -> new BankAccountManagerException(MESSAGE_FORMAT_NO_BANK_ACCOUNT));
    }
}
