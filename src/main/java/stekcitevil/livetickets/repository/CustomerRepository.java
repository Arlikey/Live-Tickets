package stekcitevil.livetickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.User;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
    Optional<Customer> findByUserEmail(String email);
}
