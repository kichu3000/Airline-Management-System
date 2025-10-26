package airlinemanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "airplanes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String airplaneId;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String status; // Active, Maintenance, Retired

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", airplaneId='" + airplaneId + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                '}';
    }
}
