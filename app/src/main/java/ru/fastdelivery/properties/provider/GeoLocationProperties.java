package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.geographic.GeoLocationProvider;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties("distance")
@Setter
public class GeoLocationProperties implements GeoLocationProvider {

    private BigDecimal baseRateDistanceKilometers;
    private BigDecimal minLatitude;
    private BigDecimal maxLatitude;
    private BigDecimal minLongitude;
    private BigDecimal maxLongitude;
    @Override
    public BigDecimal baseRateDistance() {
        return baseRateDistanceKilometers;
    }

    @Override
    public boolean isValidLatitude(BigDecimal latitudeValue) {
        return latitudeValue.compareTo(minLatitude) >= 0 &&
                latitudeValue.compareTo(maxLatitude) <= 0;
    }

    @Override
    public boolean isValidLongitude(BigDecimal longitudeValue) {
        return longitudeValue.compareTo(minLongitude) >= 0 &&
                longitudeValue.compareTo(maxLongitude) <= 0;
    }
}
