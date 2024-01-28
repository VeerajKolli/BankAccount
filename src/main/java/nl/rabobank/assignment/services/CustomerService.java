package nl.rabobank.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.assignment.entities.entity.Customer;
import nl.rabobank.assignment.exceptions.BankAccountManagerException;
import nl.rabobank.assignment.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Customer management service
 */
@Slf4j
@Service
public class CustomerService {

    private static final String MESSAGE_FORMAT_NO_CUSTOMER = "No customer by customerId: %d";

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) {

        return customerRepository.findById(customerId)
                .orElseThrow(() -> new BankAccountManagerException(MESSAGE_FORMAT_NO_CUSTOMER));
    }

    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer saved by id: {}", savedCustomer.getId());

        return savedCustomer;
    }
}
