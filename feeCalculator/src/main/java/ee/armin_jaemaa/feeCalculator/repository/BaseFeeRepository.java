package ee.armin_jaemaa.feeCalculator.repository;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BaseFeeRepository extends JpaRepository<BaseFee, Long> {

    /**
     * @param city requested delivery city
     * @param vehicleType requested delivery vehicle type
     * @param timestamp requested time
     * @return latest base fee for city,vehicle,time combination
     */
    Optional<BaseFee> findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(
            City city,
            VehicleType vehicleType,
            LocalDateTime timestamp
    );
}
