package ru.fastdelivery.domain.delivery.geographic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.fastdelivery.domain.common.geographic.Distance;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceTest {

    @Test
    @DisplayName("Запрос на перевод метры в километры -> получено корректное значение")
    void whenGetKilometers_thenReceiveKm() {
        var distance = new Distance(new BigDecimal("636019"));
        var distanceKilometers = distance.kilometers();
        assertThat(distanceKilometers).isEqualByComparingTo(new BigDecimal("636.019"));
    }
}
