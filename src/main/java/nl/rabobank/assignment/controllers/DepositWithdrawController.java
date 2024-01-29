package nl.rabobank.assignment.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import nl.rabobank.assignment.services.DepositWithdrawService;
import nl.rabobank.assignment.entities.dto.AmountDto;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(DepositWithdrawController.SERVICE_PATH)
public class DepositWithdrawController {

    public static final String SERVICE_PATH = "api/depositorwithdraw";

    private final DepositWithdrawService depositWithdrawService;

    @Autowired
    public DepositWithdrawController(DepositWithdrawService depositWithdrawService) {
        this.depositWithdrawService = depositWithdrawService;
    }


    /*Withdraw from Bank account using bank account Id*/
    @PostMapping(value = "withdraw/{bankAccountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void withdraw(@PathVariable(name = "bankAccountId") Long bankAccountId,
                         @RequestBody @Valid AmountDto amountDto) {
        log.info("/{}/{} called with amount: {}", SERVICE_PATH, bankAccountId, amountDto);

        depositWithdrawService.withdraw(bankAccountId, amountDto.getAmount());
    }

    /*Deposit to Bank account using bank account Id*/
    @PostMapping(value = "deposit/{bankAccountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deposit(@PathVariable(name = "bankAccountId") Long bankAccountId,
                         @RequestBody @Valid AmountDto amountDto) {
        log.info("/{}/{} called with amount: {}", SERVICE_PATH, bankAccountId, amountDto);

        depositWithdrawService.deposit(bankAccountId, amountDto.getAmount());
    }

}
