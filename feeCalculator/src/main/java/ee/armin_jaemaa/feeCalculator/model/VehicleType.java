package ee.armin_jaemaa.feeCalculator.model;

public enum VehicleType {
    CAR, SCOOTER, BIKE;

    public static VehicleType fromString(String value) {
        for (VehicleType v : VehicleType.values()) {
            if (v.name().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unknown vehicle: " + value);
    }
}
