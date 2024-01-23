package ru.fastdelivery.domain.delivery.geographic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.geographic.GeoLocationFactory;
import ru.fastdelivery.domain.common.geographic.GeoLocationProvider;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoLocationFactoryTest {

    final GeoLocationProvider geoLocationProvider = mock(GeoLocationProvider.class);
    GeoLocationFactory factory = new GeoLocationFactory(geoLocationProvider);
    static final BigDecimal BASE_RATE_DISTANCE_KILOMETERS = BigDecimal.valueOf(450);
    static final BigDecimal VALID_LATITUDE = BigDecimal.valueOf(55.7522);
    static final BigDecimal INVALID_LATITUDE = BigDecimal.valueOf(75.7522);
    static final BigDecimal VALID_LONGITUDE = BigDecimal.valueOf(37.6156);
    static final BigDecimal INVALID_LONGITUDE = BigDecimal.valueOf(100);

    @Test
    @DisplayName("Попытка создать объект Latitude -> получен корректный объект")
    void whenCreateLatitudeWithValidValue_thenCreatesLatitude() {

        var latitudeValue = VALID_LATITUDE;
        when(geoLocationProvider.isValidLatitude(latitudeValue)).thenReturn(true);
        var latitude = factory.createLatitude(latitudeValue);

        assertThat(latitude).isNotNull();
        assertThat(latitude.latitudeValue()).isEqualByComparingTo(latitudeValue);
    }

    @Test
    @DisplayName("Попытка передать значение больше максимального -> исключение")
    void whenCreateLatitudeWithInvalidValue_thenThrowException() {

        var latitudeValue = INVALID_LATITUDE ;
        when(geoLocationProvider.isValidLatitude(latitudeValue)).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> factory.createLatitude(latitudeValue));
    }
    @Test
    @DisplayName("Попытка создать объект Longitude -> получен корректный объект")
    void whenCreateLongitudeWithValidValue_thenCreatesLongitude() {

        var longitudeValue = VALID_LONGITUDE;
        when(geoLocationProvider.isValidLongitude(longitudeValue)).thenReturn(true);
        var longitude = factory.createLongitude(longitudeValue);

        assertThat(longitude).isNotNull();
        assertThat(longitude.longitudeValue()).isEqualByComparingTo(longitudeValue);
    }

    @Test
    @DisplayName("Попытка передать значение больше максимального -> исключение")
    void whenCreateLongitudeWithInvalidValue_thenThrowException() {

        var longitudeValue = INVALID_LONGITUDE ;
        when(geoLocationProvider.isValidLongitude(longitudeValue)).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> factory.createLongitude(longitudeValue));
    }
    @Test
    @DisplayName("Вызов метода получения базового расстояния -> получен корректный ответ")
    void whenCallBaseRateDistance_thenReturnsBaseRateDistance() {
        when(geoLocationProvider.baseRateDistance()).thenReturn(BASE_RATE_DISTANCE_KILOMETERS);
        var actualDistance = factory.baseRateDistance();
        assertThat(actualDistance).isEqualByComparingTo(BASE_RATE_DISTANCE_KILOMETERS);
    }
}
