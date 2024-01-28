package nl.rabobank.assignment.converter;

import nl.rabobank.assignment.entities.dto.BankAccountDto;
import nl.rabobank.assignment.entities.dto.CardDto;
import nl.rabobank.assignment.entities.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * BankAccount to BankAccountDto converter
 */
@Component
public class BankAccountToDtoConverter implements Converter<BankAccount, BankAccountDto> {

    private final ConversionService conversionService;

    @Autowired
    public BankAccountToDtoConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public BankAccountDto convert(BankAccount bankAccount) {
        return BankAccountDto.builder()
                .iban(bankAccount.getIban())
                .balance(bankAccount.getCurrentBalance())
                .card(conversionService.convert(bankAccount.getCard(), CardDto.class))
                .build();
    }

}
