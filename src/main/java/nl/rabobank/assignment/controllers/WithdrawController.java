package nl.rabobank.assignment.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import nl.rabobank.assignment.services.WithdrawService;
import nl.rabobank.assignment.entities.dto.AmountDto;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(WithdrawController.SERVICE_PATH)
public class WithdrawController {

    public static final String SERVICE_PATH = "api/withdraw";

    private final WithdrawService withdrawService;

    @Autowired
    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }



    @PostMapping(value = "{bankAccountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void withdraw(@PathVariable(name = "bankAccountId") Long bankAccountId,
                         @RequestBody @Valid AmountDto amountDto) {
        log.info("/{}/{} called with amount: {}", SERVICE_PATH, bankAccountId, amountDto);

        withdrawService.withdraw(bankAccountId, amountDto.getAmount());
    }

}
