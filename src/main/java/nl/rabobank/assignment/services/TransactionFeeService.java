package nl.rabobank.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.entities.enums.CardType;
import nl.rabobank.assignment.entities.enums.TransactionType;
import nl.rabobank.assignment.exceptions.BankAccountManagerException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Slf4j
@Service
public class TransactionFeeService {

    private static final MathContext mathContext = new MathContext(7, RoundingMode.HALF_UP);

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal creditCardTransferFeeMultiplier = BigDecimal.ONE.divide(ONE_HUNDRED, mathContext);
    private static final BigDecimal creditCardWithdrawFeeMultiplier = BigDecimal.ONE.divide(ONE_HUNDRED, mathContext);

    public BigDecimal getFee(TransactionType transactionType, BankAccount bankAccount, BigDecimal amount) {

        if (CardType.DEBIT_CARD.equals(bankAccount.getCard().getCardType())) {
            return BigDecimal.ZERO;
        }

        if (TransactionType.TRANSFER.equals(transactionType)) {
            return creditCardTransferFeeMultiplier.multiply(amount, mathContext);
        } else if (TransactionType.WITHDRAW.equals(transactionType)) {
            return creditCardWithdrawFeeMultiplier.multiply(amount, mathContext);
        }

        throw new BankAccountManagerException("Unknown transactionType: "+transactionType);
    }

    public BigDecimal getTotalAmount(BigDecimal amount, BigDecimal fee) {
        return amount.add(fee, mathContext);
    }

}
