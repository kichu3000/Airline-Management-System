package airlinemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirlineManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirlineManagementApplication.class, args);
    }
}

// Form → Controller → Service → Repository → Database → back through the chain
// → Response to user
