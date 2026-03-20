package ee.armin_jaemaa.feeCalculator.repository;

import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findFirstByStationNameOrderByTimestampDesc(String stationName);
}
