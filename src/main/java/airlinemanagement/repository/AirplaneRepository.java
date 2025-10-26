package airlinemanagement.repository;

import airlinemanagement.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    Optional<Airplane> findByAirplaneId(String airplaneId);
    List<Airplane> findByStatus(String status);
    boolean existsByAirplaneId(String airplaneId);
}
