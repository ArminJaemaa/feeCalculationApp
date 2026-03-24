package ee.armin_jaemaa.feeCalculator.exception;

import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;

public class BaseFeeNotFoundException extends RuntimeException {
    /**
     * @param city requested delivery city
     * @param vehicle requested delivery vehicle type
     */
    public BaseFeeNotFoundException(City city, VehicleType vehicle) {
        super("No base fee found for city " + city + " and vehicle " + vehicle);
    }
}
