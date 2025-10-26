package airlinemanagement.service;

import airlinemanagement.model.Flight;
import airlinemanagement.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, Flight updatedFlight) {
        Flight flight = getFlightById(id);
        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setSource(updatedFlight.getSource());
        flight.setDestination(updatedFlight.getDestination());
        flight.setDepartureTime(updatedFlight.getDepartureTime());
        flight.setArrivalTime(updatedFlight.getArrivalTime());
        flight.setPrice(updatedFlight.getPrice());
        flight.setTotalSeats(updatedFlight.getTotalSeats());
        return flightRepository.save(flight);
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> searchFlights(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }
}
