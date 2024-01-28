package nl.rabobank.assignment.util;

import java.math.BigDecimal;

public class ValidationUtil {

    public static boolean isNegative(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) > 0;
    }
}
