package nl.rabobank.assignment.controllers;

import nl.rabobank.assignment.entities.dto.BalanceDto;
import nl.rabobank.assignment.entities.dto.BankAccountDto;
import nl.rabobank.assignment.entities.entity.BankAccount;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(BankAccountController.SERVICE_PATH)
public class BankAccountController {

    public static final String SERVICE_PATH = "api/bank/account";
    public static final String METHOD_GET_BALANCE = "/balance";
    private static final String METHOD_GET_BALANCE_WITH_PARAM = "/balance/{bankAccountId}";
    public static final String METHOD_GET_BALANCE_ALL = "/balance/all";

    private final BankAccountService bankAccountService;
    private final ConversionService conversionService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, ConversionService conversionService)
    {
        this.bankAccountService=bankAccountService;
        this.conversionService=conversionService;
    }


    /*Create new bank account for customers*/
    @PutMapping(value = "{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveAccount(@PathVariable(name = "customerId") Long customerId,
                             @RequestBody BankAccountDto bankAccountDto) {
        log.info("/{}/{} called with bankAccountDto: {}", SERVICE_PATH, customerId, bankAccountDto);
        BankAccount bankAccount = conversionService.convert(bankAccountDto, BankAccount.class);
        if(bankAccount!=null) {
            bankAccountService.addBankAccount(customerId, bankAccount);
        }
    }
    /*Get account balance for all customers*/
    @GetMapping(value = METHOD_GET_BALANCE_ALL)
    public List<BalanceDto> getAllBalances() {
        log.info("/{}{} called", SERVICE_PATH, METHOD_GET_BALANCE_ALL);
        return bankAccountService.getBankAccountList().stream()
                .map(bankAccount -> conversionService.convert(bankAccount, BalanceDto.class))
                .collect(Collectors.toList());
    }

    /*Get balance for bank account Id*/
    @GetMapping(value = METHOD_GET_BALANCE_WITH_PARAM)
    public BalanceDto getBalance(@PathVariable(name = "bankAccountId") Long bankAccountId) {
        log.info("/{}{}/{} called", SERVICE_PATH, METHOD_GET_BALANCE, bankAccountId);
        BankAccount bankAccount = bankAccountService.getBankAccount(bankAccountId);
        return conversionService.convert(bankAccount, BalanceDto.class);
    }

}