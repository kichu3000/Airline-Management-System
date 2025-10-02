package airlinemanagement.repository;

import airlinemanagement.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // for retriving by the email
    User findByEmail(String email);

    // find by the name. The List will give you more than one data.
    List<User> findByName(String name);

    boolean existsByEmail(String email);
    // Add more in the future if I want

}
