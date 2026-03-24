package ee.armin_jaemaa.feeCalculator.repository;

import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * @param stationName requested city station name
     * @param timestamp requested time
     * @return latest station in correlation with requested time
     */
    Optional<WeatherData> findFirstByStationNameAndTimestampLessThanEqualOrderByTimestampDesc(
            String stationName,
            LocalDateTime timestamp
    );
}
