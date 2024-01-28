package nl.rabobank.assignment.entities.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.assignment.entities.enums.CardType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * card table java object
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
@EntityListeners(AuditingEntityListener.class)
public class Card {

    @Id
    @GenericGenerator(name = "card_generator")
    @GeneratedValue(generator = "card_generator")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String number;

    private String holderName;

    private String expiryDate;

    private String cvv;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}