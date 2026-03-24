package ee.armin_jaemaa.feeCalculator.exception;

import ee.armin_jaemaa.feeCalculator.model.VehicleType;

public class VehicleForbiddenException extends RuntimeException {
    /**
     * Vehicle forbidden exception based on weather conditions
     */
    public VehicleForbiddenException() {
        super("Usage of selected vehicle type is forbidden");
    }
}
