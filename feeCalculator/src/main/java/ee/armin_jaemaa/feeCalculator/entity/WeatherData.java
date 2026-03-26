package ee.armin_jaemaa.feeCalculator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_data")
@Builder
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;
    private String stationWMO;
    private double temperature;
    //private char temperatureUnit;
    private double windSpeed;
    private String weatherPhenomenon;
    private LocalDateTime timestamp;
}
