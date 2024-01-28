package nl.rabobank.assignment.converter;

import nl.rabobank.assignment.entities.dto.BalanceDto;
import nl.rabobank.assignment.entities.entity.BankAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * BankAccount to BalanceDto converter
 */
@Component
public class BankAccountToBalanceDtoConverter implements Converter<BankAccount, BalanceDto> {

    @Override
    public BalanceDto convert(BankAccount bankAccount) {
        return BalanceDto.builder()
                .bankAccountId(bankAccount.getId())
                .currentBalance(bankAccount.getCurrentBalance())
                .build();
    }

}
