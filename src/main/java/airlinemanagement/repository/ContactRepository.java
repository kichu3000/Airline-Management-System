package airlinemanagement.repository;

import airlinemanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // No need to add anything here for basic CRUD
}
