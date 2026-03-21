package ee.armin_jaemaa.feeCalculator.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "observations")
public class WeatherResponseDTO {
    @JacksonXmlProperty(isAttribute = true)
    private Long timeStamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private List<StationDTO> stations;

    @Data
    public static class StationDTO {
        @JacksonXmlProperty(localName = "name")
        private String stationName;

        @JacksonXmlProperty(localName = "wmocode")
        private String stationWMO;

        @JacksonXmlProperty(localName = "airtemperature")
        private Double temperature;

        @JacksonXmlProperty(localName = "windspeed")
        private Double windSpeed;

        @JacksonXmlProperty(localName = "phenomenon")
        private String weatherPhenomenon;

    }
}
