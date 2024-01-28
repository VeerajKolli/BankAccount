package nl.rabobank.assignment.repositories;

import nl.rabobank.assignment.entities.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * customer of bank_account table
 */
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}