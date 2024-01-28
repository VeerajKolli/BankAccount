package nl.rabobank.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.exceptions.InsufficientBalanceManagerException;
import nl.rabobank.assignment.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ValidationService {

    public void checkWithdrawable(BankAccount bankAccount, BigDecimal amount) {
        if (ValidationUtil.isNegative(bankAccount.getCurrentBalance().subtract(amount))) {
            throw new InsufficientBalanceManagerException(
                    "Account current balance is not available to withdraw. current balance: "+bankAccount.getCurrentBalance()+", amount:"+amount);
        }
    }

    public void validateCurrentBalance(BankAccount bankAccount) {
        if (ValidationUtil.isNegative(bankAccount.getCurrentBalance())) {
            throw new InsufficientBalanceManagerException(
                    "Account current balance is not available to withdraw/transfer. current balance: "+bankAccount.getCurrentBalance());
        }
    }

    public void validAmount(BigDecimal amount) {

        if(amount!=null)
        {
            if(amount.signum()<0)
            {
                throw new RuntimeException("Invalid Amount");
            }

        }
        else
        {
            throw new RuntimeException("Invalid Amount");
        }
    }
}
