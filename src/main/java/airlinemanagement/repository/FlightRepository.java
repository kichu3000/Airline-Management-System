package airlinemanagement.repository;

import airlinemanagement.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    @Query("SELECT f FROM Flight f WHERE " +
           "(:departureCity IS NULL OR LOWER(f.departureCity) LIKE LOWER(CONCAT('%', :departureCity, '%'))) AND " +
           "(:arrivalCity IS NULL OR LOWER(f.arrivalCity) LIKE LOWER(CONCAT('%', :arrivalCity, '%'))) AND " +
           "(:departureDate IS NULL OR DATE(f.departureTime) = DATE(:departureDate)) AND " +
           "f.status = 'Available'")
    List<Flight> findFlightsBySearchCriteria(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("departureDate") LocalDateTime departureDate
    );
    
    List<Flight> findByStatus(String status);
    
    List<Flight> findByAirline(String airline);
    
    List<Flight> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);
}
