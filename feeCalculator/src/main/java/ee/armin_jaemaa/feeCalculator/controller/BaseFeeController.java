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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/base-fees")
public class BaseFeeController {

    private final BaseFeeRepository baseFeeRepository;

    @GetMapping
    public List<BaseFee> getAllFees(){
        return baseFeeRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseFee addFee(@RequestBody BaseFeeRequestDTO requestDTO){

        BaseFee newFee  = new BaseFee();
        newFee.setCity(requestDTO.getCity());
        newFee.setVehicleType(requestDTO.getVehicleType());
        newFee.setFee(requestDTO.getFee());

        //check if already exists
        boolean exists = baseFeeRepository.findByCityAndVehicleType(newFee.getCity(),
                newFee.getVehicleType()).isPresent();
        if (exists){
            throw new IllegalArgumentException("fee already exists!");
        }
        return baseFeeRepository.save(newFee);
    }

    @PatchMapping("/{city}-{vehicle}")
    public BaseFee updateFee(@PathVariable City city,
                             @PathVariable VehicleType vehicle,
                             @RequestBody Double newFeeValue){
        if (newFeeValue == null || newFeeValue <= 0){
            throw new IllegalArgumentException("new fee must be positive and higher than zero!");
        }
        return baseFeeRepository.findByCityAndVehicleType(city, vehicle)
                .map(existingFee ->{
                    existingFee.setFee(newFeeValue);
                    return baseFeeRepository.save(existingFee);
                })
                .orElseThrow(()-> new BaseFeeNotFoundException(city, vehicle));
        }

    @DeleteMapping("/{city}-{vehicle}")
    public void deleteFee(@PathVariable City city,
                          @PathVariable VehicleType vehicle){
        BaseFee feeToDelete = baseFeeRepository.findByCityAndVehicleType(city, vehicle)
                .orElseThrow(()-> new BaseFeeNotFoundException(city, vehicle));
        baseFeeRepository.delete(feeToDelete);
    }
}