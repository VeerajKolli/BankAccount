package nl.rabobank.assignment.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This class is an integration class for rest services
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {
    private BigDecimal amount;
}
