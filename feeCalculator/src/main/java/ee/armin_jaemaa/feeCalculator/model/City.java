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

    /**
     * city type can be case-insensitive
     * @param value city where station is located
     * @return city station name
     */
    public static City fromString(String value) {
        for (City c : City.values()) {
            if (c.name().equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown city: " + value);
    }
}
