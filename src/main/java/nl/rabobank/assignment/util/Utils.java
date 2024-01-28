package nl.rabobank.assignment.util;

import nl.rabobank.assignment.entities.entity.Customer;

public class Utils {

    private static final String FORMAT_CARD_HOLDER = "%s %s";

    public static String getCardHolderName(Customer customer) {
        return String.format(FORMAT_CARD_HOLDER, customer.getInitial(), customer.getLastName());
    }
}
