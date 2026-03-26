package ee.armin_jaemaa.feeCalculator.service;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import ee.armin_jaemaa.feeCalculator.exception.BaseFeeNotFoundException;
import ee.armin_jaemaa.feeCalculator.exception.VehicleForbiddenException;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.repository.BaseFeeRepository;
import ee.armin_jaemaa.feeCalculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private final WeatherDataRepository weatherDataRepository;
    private final BaseFeeRepository baseFeeRepository;

    /**
     * @param city requested delivery city
     * @param vehicle requested vehicle used
     * @param requestedTime requested time of delivery
     * @return total fee based on city, vehicle and time. extra fees are added if needed
     */
    public double calculateTotalFee(City city, VehicleType vehicle, LocalDateTime requestedTime) {

        LocalDateTime effectiveTime = (requestedTime != null) ? requestedTime : LocalDateTime.now();

        double baseDeliveryFee = getBaseFee(city, vehicle, effectiveTime);
        double extraDeliveryFee = calculateExtraFees(city, vehicle, effectiveTime);
        return baseDeliveryFee + extraDeliveryFee;
    }

    public double getBaseFee(City city, VehicleType vehicle, LocalDateTime requestedTime) {

        return baseFeeRepository.findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(city, vehicle, requestedTime)
                .map(BaseFee::getFee)
                .orElseThrow(() -> new BaseFeeNotFoundException(city, vehicle));
    }

    public double calculateExtraFees(City city, VehicleType vehicle, LocalDateTime requestedTime) {
        WeatherData weather = weatherDataRepository.findFirstByStationNameAndTimestampLessThanEqualOrderByTimestampDesc(city.getStationName(), requestedTime)
                .orElseThrow(() -> new RuntimeException("No weather data found for city " + city));

        double totalExtraFee = 0.0;

        if (vehicle == VehicleType.SCOOTER || vehicle == VehicleType.BIKE) {
            totalExtraFee += getTemperatureExtraFee(weather.getTemperature());
            if (vehicle == VehicleType.BIKE) {
                totalExtraFee += getWindSpeedExtraFee(weather.getWindSpeed());
            }
            totalExtraFee += getPhenomenonExtraFee(weather.getWeatherPhenomenon());
        }
        return totalExtraFee;
    }

    public double getTemperatureExtraFee(Double temperature) {
        if (temperature == null) return 0;

        //add extra fee
        if (temperature < -10) return 1.0;
        if (temperature <= 0) return 0.5;

        return 0;
    }
    public double getWindSpeedExtraFee(Double windSpeed) {
        if (windSpeed == null) return 0;

        //check for forbidden condition
        if (windSpeed > 20) throw new VehicleForbiddenException();

        //add extra fee
        if  (windSpeed >= 10 && windSpeed < 20) return 0.5;

        return 0;
    }
    public double getPhenomenonExtraFee(String phenomenon) {
        if (phenomenon == null || phenomenon.isEmpty()) return 0;

        String phenomenonToCheck = phenomenon.toLowerCase();

        //Check for forbidden conditions
        if (phenomenonToCheck.contains("hail") || phenomenonToCheck.contains("glaze") || phenomenonToCheck.contains("thunder")) {
            throw new  VehicleForbiddenException();
        }
        //add extra fees
        if (phenomenonToCheck.contains("snow") || phenomenonToCheck.contains("sleet")) return 1.0;
        if (phenomenonToCheck.contains("rain")) return 0.5;

        return 0;
    }
}
