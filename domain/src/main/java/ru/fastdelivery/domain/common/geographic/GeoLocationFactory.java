package ru.fastdelivery.domain.common.geographic;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class GeoLocationFactory {
    private final GeoLocationProvider geoLocationProvider;

    public Latitude createLatitude(BigDecimal latitudeValue) {
        if (latitudeValue == null || !geoLocationProvider.isValidLatitude(latitudeValue)) {
            throw new IllegalArgumentException("Latitude has not valid value");
        }
        return new Latitude(latitudeValue);
    }

    public Longitude createLongitude(BigDecimal longitudeValue) {
        if (longitudeValue == null || !geoLocationProvider.isValidLongitude(longitudeValue)) {
            throw new IllegalArgumentException("Longitude has not valid value");
        }
        return new Longitude(longitudeValue);
    }

    public BigDecimal baseRateDistance() {
        return geoLocationProvider.baseRateDistance();
    }
}
