package nl.rabobank.assignment.converter;

import nl.rabobank.assignment.entities.dto.CustomerDto;
import nl.rabobank.assignment.entities.entity.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Customer to CustomerDto converter
 */
@Component
public class CustomerToDtoConverter implements Converter<Customer, CustomerDto> {

    @Override
    public CustomerDto convert(Customer customer) {

        return CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .initial(customer.getInitial())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

}
