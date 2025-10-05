package airlinemanagement.service;

import airlinemanagement.model.Flight;
import airlinemanagement.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {
    
    private final FlightRepository flightRepository;
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public List<Flight> searchFlights(String departureCity, String arrivalCity, String departureDate) {
        LocalDateTime dateTime = null;
        if (departureDate != null && !departureDate.isEmpty()) {
            try {
                dateTime = LocalDateTime.parse(departureDate + "T00:00:00");
            } catch (Exception e) {
                // If date parsing fails, search without date filter
            }
        }
        
        return flightRepository.findFlightsBySearchCriteria(
            departureCity, 
            arrivalCity, 
            dateTime
        );
    }
    
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    public List<Flight> getAvailableFlights() {
        return flightRepository.findByStatus("Available");
    }
    
    public List<Flight> getFlightsByAirline(String airline) {
        return flightRepository.findByAirline(airline);
    }
    
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
    
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    
    public String formatFlightTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("h:mm a"));
    }
    
    public String formatFlightDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
    
    public String formatDuration(Integer durationMinutes) {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        if (minutes == 0) {
            return hours + "h";
        }
        return hours + "h " + minutes + "m";
    }
    
    public String getFormattedDuration(Integer durationMinutes) {
        return formatDuration(durationMinutes);
    }
}
