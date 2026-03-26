package ee.armin_jaemaa.feeCalculator.controller;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Base Fee Management", description = "CRUD endpoints to manage regional base fee rules")
public interface BaseFeeApi {

    @Operation(summary = "Get all rules", description = "Retrieve a list of all existing city/vehicle fee rules")
    List<BaseFee> getAllFees();

    @Operation(summary = "Update a fee", description = "Updates the price for a specific city and vehicle combination")
    BaseFee updateFee(
            @Parameter(description = "The city to update", example = "Tallinn") City city,
            @Parameter(description = "The vehicle to update", example = "Car") VehicleType vehicle,
            @RequestBody Double newPrice
    );
}
