package ee.armin_jaemaa.feeCalculator.controller;

import ee.armin_jaemaa.feeCalculator.dto.BaseFeeRequestDTO;
import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.exception.BaseFeeNotFoundException;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.repository.BaseFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/base-fees")
public class BaseFeeController {

    private final BaseFeeRepository baseFeeRepository;

    /**
     * @return returns all base fees
     */
    @GetMapping
    public List<BaseFee> getAllFees(){
        return baseFeeRepository.findAll();
    }

    /**
     * @param requestDTO data transfer object to create a base fee (city and vehicle)
     * @return adds new base fee combination with requested city and vehicle type
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseFee addFee(@RequestBody BaseFeeRequestDTO requestDTO){

        BaseFee newFee  = new BaseFee();
        newFee.setCity(requestDTO.getCity());
        newFee.setVehicleType(requestDTO.getVehicleType());
        newFee.setFee(requestDTO.getFee());
        newFee.setTimestamp(LocalDateTime.now());

        //check if fee already exists
        /*boolean exists = baseFeeRepository.findByCityAndVehicleType(newFee.getCity(),
                newFee.getVehicleType()).isPresent();
        if (exists){
            throw new IllegalArgumentException("fee already exists!");
        } */
        return baseFeeRepository.save(newFee);
    }

    /**
     * @param city requested city to be changed
     * @param vehicle requested vehicle type to be changed
     * @param newFeeValue new base fee for city and vehicle type
     * @return creates new insertion with requested combination with new datetime
     */
    @PatchMapping("/{city}-{vehicle}")
    public BaseFee updateFee(@PathVariable City city,
                             @PathVariable VehicleType vehicle,
                             @RequestBody Double newFeeValue){
        if (newFeeValue == null || newFeeValue <= 0){
            throw new IllegalArgumentException("new fee must be positive and higher than zero!");
        }
        LocalDateTime timestamp = LocalDateTime.now();
         baseFeeRepository.findFirstByCityAndVehicleTypeAndTimestampLessThanEqualOrderByTimestampDesc(city, vehicle, timestamp)
                .orElseThrow(()-> new BaseFeeNotFoundException(city, vehicle));

        BaseFee historyEntry =new BaseFee();
                historyEntry.setCity(city);
                historyEntry.setVehicleType(vehicle);
                historyEntry.setFee(newFeeValue);
                historyEntry.setTimestamp(LocalDateTime.now());

                return baseFeeRepository.save(historyEntry);
    }

    /**
     * @param id fee id to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteFee(@PathVariable Long id){
        BaseFee feeToDelete = baseFeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No base fee found for id " + id));
        baseFeeRepository.delete(feeToDelete);
    }
}