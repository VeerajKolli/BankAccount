package nl.rabobank.assignment.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.dto.CustomerDto;
import nl.rabobank.assignment.entities.entity.Customer;
import nl.rabobank.assignment.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is created to manage customer process
 */
@Slf4j
@RestController

@RequestMapping(CustomerController.SERVICE_PATH)
public class CustomerController {

    public static final String SERVICE_PATH = "api/customer";
    public static final String METHOD_GET_ALL = "/all";

    private final CustomerService customerService;
    private final ConversionService conversionService;

    @Autowired
    public CustomerController(CustomerService customerService, ConversionService conversionService)
    {
        this.customerService=customerService;
        this.conversionService=conversionService;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        log.info("/{} called with customerId: {}", (Object) SERVICE_PATH, customerDto);

        customerService.saveCustomer(conversionService.convert(customerDto, Customer.class));
    }

    @GetMapping(value = METHOD_GET_ALL)
    public List<CustomerDto> getAllCustomers() {
        log.info("/{}{} called", SERVICE_PATH, METHOD_GET_ALL);

        return customerService.getCustomerList().stream()
                .map(customer -> conversionService.convert(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/{customerId}")
    public CustomerDto getCustomer(@PathVariable(name = "customerId") Long customerId) {
        log.info("/{}/{} called", SERVICE_PATH, customerId);
        Customer customer = customerService.getCustomer(customerId);

        return conversionService.convert(customer, CustomerDto.class);
    }

}
