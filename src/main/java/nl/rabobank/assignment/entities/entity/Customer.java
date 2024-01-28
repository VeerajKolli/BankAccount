package nl.rabobank.assignment.entities.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.CascadeType.ALL;

/**
 * customer table java object
 */
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GenericGenerator(name = "customer_generator")
    @GeneratedValue(generator = "customer_generator")
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String initial;

    private String email;

    private String phoneNumber;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @ToString.Exclude
    @OneToOne(optional = false, cascade = ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    private BankAccount bankAccount;

    public Customer() {

    }
}
