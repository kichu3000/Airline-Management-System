package airlinemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String airline;
    private String departureCity;
    private String departureAirport;
    private String arrivalCity;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private Integer duration; // in minutes
    private String status; // "Available", "Full", "Cancelled"
    private String aircraftType;
    private String airlineLogo;
    
    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;
}
