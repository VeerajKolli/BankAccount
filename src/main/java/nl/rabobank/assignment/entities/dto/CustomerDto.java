package nl.rabobank.assignment.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is an integration class for rest services
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String firstName;
    private String lastName;
    private String initial;
    private String email;
    private String phoneNumber;

}
