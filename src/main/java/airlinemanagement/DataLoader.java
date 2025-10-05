package airlinemanagement;

import airlinemanagement.model.Airplane;
import airlinemanagement.model.Flight;
import airlinemanagement.repository.AirplaneRepository;
import airlinemanagement.repository.FlightRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {
    private final AirplaneRepository airplaneRepo;
    private final FlightRepository flightRepo;

    public DataLoader(AirplaneRepository airplaneRepo, FlightRepository flightRepo) { 
        this.airplaneRepo = airplaneRepo; 
        this.flightRepo = flightRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Load airplanes if none exist
        if (airplaneRepo.count() == 0) {
            airplaneRepo.save(Airplane.builder().model("Boeing 737").registration("N737QJ").manufacturer("Boeing").capacity(160).imagePath("/images/jet3.jpg").build());
            airplaneRepo.save(Airplane.builder().model("Airbus A320").registration("N320QJ").manufacturer("Airbus").capacity(180).imagePath("/images/loginPlane.jpg").build());
            airplaneRepo.save(Airplane.builder().model("Embraer 175").registration("N175QJ").manufacturer("Embraer").capacity(88).imagePath("/images/loginPlane2.jpg").build());
        }
        
        // Load flights if none exist
        if (flightRepo.count() == 0) {
            // Get airplanes for flight assignments
            Airplane boeing737 = airplaneRepo.findByRegistration("N737QJ").orElse(null);
            Airplane airbus320 = airplaneRepo.findByRegistration("N320QJ").orElse(null);
            Airplane embraer175 = airplaneRepo.findByRegistration("N175QJ").orElse(null);
            
            // Sample flights
            flightRepo.save(Flight.builder()
                .flightNumber("QJ205")
                .airline("QuinJet Airways")
                .departureCity("New York")
                .departureAirport("JFK")
                .arrivalCity("London")
                .arrivalAirport("LHR")
                .departureTime(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0))
                .arrivalTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                .price(350.0)
                .duration(150) // 2h 30m
                .status("Available")
                .aircraftType("Boeing 737")
                .airlineLogo("https://placehold.co/48x48/0EA5E9/FFFFFF?text=QJ")
                .airplane(boeing737)
                .build());
                
            flightRepo.save(Flight.builder()
                .flightNumber("SH712")
                .airline("SkyHigh Airlines")
                .departureCity("New York")
                .departureAirport("JFK")
                .arrivalCity("London")
                .arrivalAirport("LHR")
                .departureTime(LocalDateTime.now().plusDays(1).withHour(9).withMinute(15))
                .arrivalTime(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0))
                .price(320.0)
                .duration(165) // 2h 45m
                .status("Available")
                .aircraftType("Airbus A320")
                .airlineLogo("https://placehold.co/48x48/EC4899/FFFFFF?text=SH")
                .airplane(airbus320)
                .build());
                
            flightRepo.save(Flight.builder()
                .flightNumber("AA123")
                .airline("Atlantic Airways")
                .departureCity("Los Angeles")
                .departureAirport("LAX")
                .arrivalCity("New York")
                .arrivalAirport("JFK")
                .departureTime(LocalDateTime.now().plusDays(2).withHour(14).withMinute(30))
                .arrivalTime(LocalDateTime.now().plusDays(2).withHour(22).withMinute(45))
                .price(280.0)
                .duration(495) // 8h 15m
                .status("Available")
                .aircraftType("Boeing 737")
                .airlineLogo("https://placehold.co/48x48/10B981/FFFFFF?text=AA")
                .airplane(boeing737)
                .build());
                
            flightRepo.save(Flight.builder()
                .flightNumber("PA456")
                .airline("Pacific Airlines")
                .departureCity("Chicago")
                .departureAirport("ORD")
                .arrivalCity("Miami")
                .arrivalAirport("MIA")
                .departureTime(LocalDateTime.now().plusDays(3).withHour(11).withMinute(0))
                .arrivalTime(LocalDateTime.now().plusDays(3).withHour(15).withMinute(30))
                .price(180.0)
                .duration(270) // 4h 30m
                .status("Available")
                .aircraftType("Embraer 175")
                .airlineLogo("https://placehold.co/48x48/F59E0B/FFFFFF?text=PA")
                .airplane(embraer175)
                .build());
                
            flightRepo.save(Flight.builder()
                .flightNumber("EU789")
                .airline("European Express")
                .departureCity("Paris")
                .departureAirport("CDG")
                .arrivalCity("Rome")
                .arrivalAirport("FCO")
                .departureTime(LocalDateTime.now().plusDays(4).withHour(16).withMinute(45))
                .arrivalTime(LocalDateTime.now().plusDays(4).withHour(19).withMinute(15))
                .price(220.0)
                .duration(150) // 2h 30m
                .status("Available")
                .aircraftType("Airbus A320")
                .airlineLogo("https://placehold.co/48x48/8B5CF6/FFFFFF?text=EU")
                .airplane(airbus320)
                .build());
        }
    }
}
