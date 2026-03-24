package ee.armin_jaemaa.feeCalculator.controller;

import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.service.DeliveryFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryFeeController implements DeliveryFeeApi {
    private final DeliveryFeeService deliveryFeeService;

    @GetMapping("/delivery-fee")
    public ResponseEntity<Double> getDeliveryFee(
            @RequestParam  String city,
            @RequestParam String vehicle,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")LocalDateTime timestamp) {

        City cityObj = City.fromString(city);
        VehicleType vehicleObj = VehicleType.fromString(vehicle);

        double totalFee = deliveryFeeService.calculateTotalFee(cityObj, vehicleObj, timestamp);
        return ResponseEntity.ok(totalFee);
    }
}
