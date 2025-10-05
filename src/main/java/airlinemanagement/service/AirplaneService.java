package airlinemanagement.service;

import airlinemanagement.model.Airplane;
import airlinemanagement.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneService {
    private final AirplaneRepository repo;

    public AirplaneService(AirplaneRepository repo) {
        this.repo = repo;
    }

    public List<Airplane> findAll() { return repo.findAll(); }
    public Optional<Airplane> findById(Long id) { return repo.findById(id); }
    public Airplane save(Airplane a) { return repo.save(a); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
