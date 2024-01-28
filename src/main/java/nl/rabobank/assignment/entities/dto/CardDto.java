package nl.rabobank.assignment.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.assignment.entities.enums.CardType;

/**
 * This class is an integration class for rest services
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private CardType cardType;
    private String number;
    private String expiryDate;
    private String cvv;
}
