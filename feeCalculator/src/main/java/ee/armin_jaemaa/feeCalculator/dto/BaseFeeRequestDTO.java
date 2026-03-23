package ee.armin_jaemaa.feeCalculator.dto;

import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BaseFeeRequestDTO {
    @NotNull
    private City city;

    @NotNull
    private VehicleType vehicleType;

    @Positive
    private double fee;
}
