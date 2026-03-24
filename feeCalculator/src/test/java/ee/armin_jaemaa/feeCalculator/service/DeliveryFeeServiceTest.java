package ee.armin_jaemaa.feeCalculator.service;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import ee.armin_jaemaa.feeCalculator.exception.VehicleForbiddenException;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.repository.BaseFeeRepository;
import ee.armin_jaemaa.feeCalculator.repository.WeatherDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryFeeServiceTest {
    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private BaseFeeRepository baseFeeRepository;

    @InjectMocks
    private DeliveryFeeService deliveryFeeService;

    @ParameterizedTest
    @EnumSource(value = VehicleType.class, names = {"BIKE", "SCOOTER"})
    public void windSpeedTooHighTest(VehicleType vehicle) {
        BaseFee mockBaseFee = new BaseFee(1L, City.TALLINN, vehicle, 3.0, LocalDateTime.now());
        WeatherData mockWeather = WeatherData.builder()
                .stationName("Tallinn-Harku")
                .windSpeed(100.0)
                .weatherPhenomenon("Clear")
                .temperature(15.0)
                .build();

        when(baseFeeRepository.findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(eq(City.TALLINN), eq(vehicle), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockBaseFee));
        when(weatherDataRepository.findFirstByStationNameAndTimestampLessThanEqualOrderByTimestampDesc(eq("Tallinn-Harku"),any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockWeather));

        VehicleForbiddenException exception = assertThrows(VehicleForbiddenException.class, () -> {
            deliveryFeeService.calculateTotalFee(City.TALLINN, vehicle, LocalDateTime.now());
        });
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }

    @Test
    public void calculateTotalFeeTest() {
        BaseFee mockBaseFee =  new BaseFee(1L, City.TARTU, VehicleType.CAR, 3.5,  LocalDateTime.now());
        WeatherData mockWeather = WeatherData.builder()
                .stationName("Tartu-Tõravere")
                .windSpeed(100.0)
                .weatherPhenomenon("Clear")
                .temperature(15.0)
                .build();

        when(baseFeeRepository.findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(eq(City.TARTU), eq(VehicleType.CAR), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockBaseFee));
        when(weatherDataRepository.findFirstByStationNameAndTimestampLessThanEqualOrderByTimestampDesc(eq("Tartu-Tõravere"), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockWeather));

        assertEquals(3.5, deliveryFeeService.calculateTotalFee(City.TARTU, VehicleType.CAR,  LocalDateTime.now()));
    }

    @ParameterizedTest
    @CsvSource({
            "BIKE, Thunder, -20, 3, true",
            "CAR, Hail, -15, 4, false",
            "SCOOTER, Clear,-15, 3.5, false",
            "BIKE, Clear, -5, 3, false"
    })
    public void calculateVehicleExtraFeeTest(VehicleType vehicle, String phenomenon, double temperature, double fee, boolean expectedFail) {
        BaseFee mockBaseFee = new BaseFee(1L, City.TALLINN, vehicle, fee, LocalDateTime.now());

        WeatherData mockWeather = WeatherData.builder()
                .stationName("Tallinn-Harku")
                .windSpeed(4.0)
                .weatherPhenomenon(phenomenon)
                .temperature(temperature)
                .build();

        when(baseFeeRepository.findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(eq(City.TALLINN), eq(vehicle), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockBaseFee));
        when(weatherDataRepository.findFirstByStationNameAndTimestampLessThanEqualOrderByTimestampDesc(eq("Tallinn-Harku"), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockWeather));

        if(expectedFail) {
            assertThrows(VehicleForbiddenException.class, () -> {
                deliveryFeeService.calculateTotalFee(City.TALLINN, vehicle,  LocalDateTime.now());
            });
            //System.out.println("expectedFail");
        }else{
            double result = deliveryFeeService.calculateTotalFee(City.TALLINN, vehicle,   LocalDateTime.now());
            //System.out.println(result);
            assertTrue(result >= fee, "Total fee has to be atleast the same as base fee");
        }
    }
}
