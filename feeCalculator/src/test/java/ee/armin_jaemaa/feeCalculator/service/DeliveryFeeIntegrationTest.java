package ee.armin_jaemaa.feeCalculator.service;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.repository.BaseFeeRepository;
import ee.armin_jaemaa.feeCalculator.repository.WeatherDataRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DeliveryFeeIntegrationTest {
    @Autowired
    private DeliveryFeeService deliveryFeeService;

    @Autowired
    private BaseFeeRepository baseFeeRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Test
    public void deliveryFeeHistoryTest()
    {
        LocalDateTime past = LocalDateTime.of(2026, 2, 23, 10, 0);
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 15, 0);

        baseFeeRepository.save(new BaseFee(null, City.TALLINN, VehicleType.CAR, 4.0, now));
        baseFeeRepository.save(new BaseFee(null, City.TALLINN, VehicleType.CAR, 10.0, past));

        weatherDataRepository.save(WeatherData.builder()
                .stationName(City.TALLINN.getStationName())
                .temperature(20.0).windSpeed(0.0).weatherPhenomenon("Clear")
                .timestamp(past).build());

        LocalDateTime pastCheckTime = LocalDateTime.of(2026, 3, 1, 10, 0);
        Double result = deliveryFeeService.calculateTotalFee(City.TALLINN, VehicleType.CAR, pastCheckTime);

        assertEquals(10.0, result);
    }
}
