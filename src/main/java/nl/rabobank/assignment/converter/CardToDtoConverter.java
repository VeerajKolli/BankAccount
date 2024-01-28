package nl.rabobank.assignment.converter;
import nl.rabobank.assignment.entities.dto.CardDto;
import nl.rabobank.assignment.entities.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Card to CardDto converter
 */
@Component
public class CardToDtoConverter implements Converter<Card, CardDto> {

    @Override
    public CardDto convert(Card card) {

        return CardDto.builder()
                .cardType(card.getCardType())
                .number(card.getNumber())
                .expiryDate(card.getExpiryDate())
                .cvv(card.getCvv())
                .build();
    }

}
