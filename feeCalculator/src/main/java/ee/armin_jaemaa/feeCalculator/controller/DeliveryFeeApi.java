package ee.armin_jaemaa.feeCalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Tag(name="Delivery Fee", description = "deliveryFeeCalculator app endpoints")
public interface DeliveryFeeApi {
    @Operation(
            summary = "Calculate delivery fee",
            description = "Calculates the total fee based on regional base fees and weather conditions." +
                    "Supports historical calculations if timestamp is provided"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated fee"),
            @ApiResponse(responseCode = "400", description = "Invalid input or vehicle usage is forbidden due to weather")
    })
    ResponseEntity<Double> getDeliveryFee(
            @Parameter(description = "City name", example = "Tallinn") String city,
            @Parameter(description = "Vehicle type", example = "Car") String vehicle,
            @Parameter(description = "Optional: Historical time (yyyy-MM-dd HH:mm)", example = "2024-03-24 14:15")
            LocalDateTime timestamp
    );
}
