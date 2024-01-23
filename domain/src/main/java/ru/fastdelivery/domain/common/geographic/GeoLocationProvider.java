package ru.fastdelivery.domain.common.geographic;

import java.math.BigDecimal;

public interface GeoLocationProvider {

    BigDecimal baseRateDistance();
    boolean isValidLatitude(BigDecimal latitudeValue);
    boolean isValidLongitude(BigDecimal longitudeValue);
}
