package ee.armin_jaemaa.feeCalculator.model;

import lombok.Getter;

@Getter
public enum City {
    TALLINN("Tallinn-Harku"),
    TARTU("Tartu-Tõravere"),
    PÄRNU("Pärnu");

    private final String stationName;

    City(String stationName) {
        this.stationName = stationName;
    }
}
