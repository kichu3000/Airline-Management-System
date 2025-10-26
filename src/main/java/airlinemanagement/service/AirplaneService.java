package airlinemanagement.service;

import airlinemanagement.model.Airplane;
import airlinemanagement.repository.AirplaneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Optional<Airplane> getAirplaneById(Long id) {
        return airplaneRepository.findById(id);
    }

    public Optional<Airplane> getAirplaneByAirplaneId(String airplaneId) {
        return airplaneRepository.findByAirplaneId(airplaneId);
    }

    public List<Airplane> getAirplanesByStatus(String status) {
        return airplaneRepository.findByStatus(status);
    }

    @Transactional
    public Airplane createAirplane(Airplane airplane) {
        if (airplaneRepository.existsByAirplaneId(airplane.getAirplaneId())) {
            throw new IllegalArgumentException("Airplane ID already exists");
        }
        return airplaneRepository.save(airplane);
    }

    @Transactional
    public Airplane updateAirplane(Long id, Airplane airplaneDetails) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found"));

        airplane.setModel(airplaneDetails.getModel());
        airplane.setCapacity(airplaneDetails.getCapacity());
        airplane.setStatus(airplaneDetails.getStatus());

        return airplaneRepository.save(airplane);
    }

    @Transactional
    public void deleteAirplane(Long id) {
        if (!airplaneRepository.existsById(id)) {
            throw new IllegalArgumentException("Airplane not found");
        }
        airplaneRepository.deleteById(id);
    }

    public boolean airplaneExists(String airplaneId) {
        return airplaneRepository.existsByAirplaneId(airplaneId);
    }
}

