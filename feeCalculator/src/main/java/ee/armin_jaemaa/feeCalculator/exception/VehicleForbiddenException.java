package ee.armin_jaemaa.feeCalculator.exception;

public class VehicleForbiddenException extends RuntimeException {
    /**
     * Vehicle forbidden exception based on weather conditions
     */
    public VehicleForbiddenException() {
        super("Usage of selected vehicle type is forbidden");
    }
}
