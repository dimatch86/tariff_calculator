package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.GeoLocationProperties;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class GeoLocationPropertiesTest {
    static final BigDecimal BASE_RATE_DISTANCE_KILOMETERS = BigDecimal.valueOf(450);
    static final BigDecimal VALID_LATITUDE = BigDecimal.valueOf(55.7522);
    static final BigDecimal INVALID_LATITUDE = BigDecimal.valueOf(75.7522);
    static final BigDecimal VALID_LONGITUDE = BigDecimal.valueOf(37.6156);
    static final BigDecimal INVALID_LONGITUDE = BigDecimal.valueOf(100);
    GeoLocationProperties properties;

    @BeforeEach
    void init() {
        properties = new GeoLocationProperties();
        properties.setBaseRateDistanceKilometers(BASE_RATE_DISTANCE_KILOMETERS);
        properties.setMinLatitude(BigDecimal.valueOf(45));
        properties.setMaxLatitude(BigDecimal.valueOf(65));
        properties.setMinLongitude(BigDecimal.valueOf(30));
        properties.setMaxLongitude(BigDecimal.valueOf(96));
    }

    @Test
    void whenCallBaseRateDistance_thenRequestFromConfig() {
        var actual = properties.baseRateDistance();
        assertThat(actual).isEqualTo(BASE_RATE_DISTANCE_KILOMETERS);
    }

    @Test
    void whenCheckValidLatitude_thenReturnsTrue() {
        var isValid = properties.isValidLatitude(VALID_LATITUDE);
        assertThat(isValid).isTrue();
    }

    @Test
    void whenCheckInvalidLatitude_thenReturnsFalse() {
        var isValid = properties.isValidLatitude(INVALID_LATITUDE);
        assertThat(isValid).isFalse();
    }

    @Test
    void whenCheckValidLongitude_thenReturnsTrue() {
        var isValid = properties.isValidLongitude(VALID_LONGITUDE);
        assertThat(isValid).isTrue();
    }

    @Test
    void whenCheckInvalidLongitude_thenReturnsFalse() {
        var isValid = properties.isValidLongitude(INVALID_LONGITUDE);
        assertThat(isValid).isFalse();
    }
}
