package ee.armin_jaemaa.feeCalculator.service;

import ee.armin_jaemaa.feeCalculator.dto.WeatherResponseDTO;
import ee.armin_jaemaa.feeCalculator.entity.WeatherData;
import ee.armin_jaemaa.feeCalculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherImportService {
    private final WeatherDataRepository weatherDataRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String WEATHER_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private static final Set<String> TARGET_STATIONS = Set.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");

    @Scheduled(cron = "${weather.import.cron:0 15 * * * *}")
    public void fetchWeatherData() {
        log.info("scheduled weather data import...");
        try{
            WeatherResponseDTO response = restTemplate.getForObject(WEATHER_URL, WeatherResponseDTO.class);

            if (response != null && response.getStations() != null && response.getTimestamp() != null) {
                LocalDateTime observationTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(response.getTimestamp()), ZoneId.systemDefault());
                response.getStations().stream()
                        .filter(station -> TARGET_STATIONS.contains(station.getStationName()))
                        .forEach(station -> {
                            WeatherData entity = WeatherData.builder()
                                    .stationName(station.getStationName())
                                    .stationWMO(station.getStationWMO())
                                    .temperature(station.getTemperature())
                                    .windSpeed(station.getWindSpeed())
                                    .weatherPhenomenon(station.getWeatherPhenomenon())
                                    .timestamp(observationTime)
                                    .build();

                            weatherDataRepository.save(entity);
                            log.info("weather data imported for station {}", station.getStationName());
                        });
            }
        }catch (Exception e){
            log.error("failed scheduled fetch of weather data",e);
        }
    }
}
