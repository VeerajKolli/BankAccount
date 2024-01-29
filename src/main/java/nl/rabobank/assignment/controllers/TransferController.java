package nl.rabobank.assignment.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.dto.AmountDto;
import nl.rabobank.assignment.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class is created to manage transfer process
 */
@Slf4j
@RestController
@RequestMapping(TransferController.SERVICE_PATH)
public class TransferController {

    public static final String SERVICE_PATH = "api/transfer";

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }



    /*Transfer from Bank account to another bank account using bank account Ids*/
    @PostMapping(value = "{fromBankAccountId}/{toBankAccountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void transfer( @PathVariable(name = "fromBankAccountId") Long fromBankAccountId,
                          @PathVariable(name = "toBankAccountId") Long toBankAccountId,
                          @RequestBody @Valid AmountDto amountDto) {
        log.info("{} {} {} called with amount: {}", SERVICE_PATH, fromBankAccountId, toBankAccountId, amountDto);

        transferService.transfer(fromBankAccountId, toBankAccountId, amountDto.getAmount());
    }
}