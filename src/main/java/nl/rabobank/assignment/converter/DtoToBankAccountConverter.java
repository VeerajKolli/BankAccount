package nl.rabobank.assignment.converter;

import nl.rabobank.assignment.entities.dto.BankAccountDto;
import nl.rabobank.assignment.entities.entity.BankAccount;
import nl.rabobank.assignment.entities.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * BankAccountDto to BankAccount converter
 */
@Component
public class DtoToBankAccountConverter implements Converter<BankAccountDto, BankAccount> {


    private ConversionService conversionService;

    /*
     * CAUTION: service must be lazy, because you cannot simply inject  a service that is not yet ready.
     * We are already still building the converters in this step.
     * */
    @Autowired
    public DtoToBankAccountConverter(@Lazy ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public BankAccount convert(BankAccountDto bankAccount) {
        return BankAccount.builder()
                .iban(bankAccount.getIban())
                .currentBalance(bankAccount.getBalance())
                .card(conversionService.convert(bankAccount.getCard(), Card.class))
                .build();
    }

}
