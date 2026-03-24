package ee.armin_jaemaa.feeCalculator.model;

public enum VehicleType {
    CAR, SCOOTER, BIKE;

    /**
     * vehicle type can be case-insensitive
     * @param value vehicle type
     * @return returns vehicle type
     */
    public static VehicleType fromString(String value) {
        for (VehicleType v : VehicleType.values()) {
            if (v.name().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unknown vehicle: " + value);
    }
}
