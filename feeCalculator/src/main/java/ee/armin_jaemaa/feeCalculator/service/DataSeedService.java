package ee.armin_jaemaa.feeCalculator.service;

import ee.armin_jaemaa.feeCalculator.entity.BaseFee;
import ee.armin_jaemaa.feeCalculator.model.City;
import ee.armin_jaemaa.feeCalculator.model.VehicleType;
import ee.armin_jaemaa.feeCalculator.repository.BaseFeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSeedService {

    private final BaseFeeRepository baseFeeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedBaseFees() {
        //baseFeeRepository.deleteAll();
        if  (baseFeeRepository.count() == 0) {
            log.info("Seeding initial fee data");
            baseFeeRepository.saveAll(List.of(
                    //TALLINN
                    new BaseFee(null, City.TALLINN, VehicleType.CAR, 4.0),
                    new BaseFee(null, City.TALLINN, VehicleType.SCOOTER, 3.5),
                    new BaseFee(null, City.TALLINN, VehicleType.BIKE, 3.0),

                    //TARTU
                    new BaseFee(null, City.TARTU, VehicleType.CAR, 3.5),
                    new BaseFee(null, City.TARTU, VehicleType.SCOOTER, 3.0),
                    new BaseFee(null, City.TARTU, VehicleType.BIKE, 2.5),

                    //PÄRNU
                    new BaseFee(null, City.PÄRNU, VehicleType.CAR, 3.0),
                    new BaseFee(null, City.PÄRNU, VehicleType.SCOOTER, 2.5),
                    new BaseFee(null, City.PÄRNU, VehicleType.BIKE, 2.0)
            ));
        }
    }
}
