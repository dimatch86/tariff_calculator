package ru.fastdelivery.domain.common.dimensions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.volume.Dimension;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DimensionTest {

    @Test
    @DisplayName("Попытка передать отрицательный габарит -> исключение")
    void whenLengthBelowZero_thenException() {
        var lengthMillimeters = new BigInteger("-15");
        assertThatThrownBy(() -> new Dimension(lengthMillimeters))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Попытка передать габарит больше максимального -> исключение")
    void whenLengthMoreThanMaxLength_thenThrowException() {
        var length = BigInteger.valueOf(1501);
        assertThatThrownBy(() -> new Dimension(length))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Округление габарита кратно 50")
    void whenNormalizeLength_thenReturnsNormalizedLengthValue() {

        var length = new Dimension(BigInteger.valueOf(224));
        var normalizedLengthValue = length.getNormalizedDimension();
        var intValue = normalizedLengthValue.intValue();

        assertThat(intValue % 50).isZero();
        assertThat(normalizedLengthValue).isNotEqualByComparingTo(String.valueOf(250));
    }

    @Test
    void equalsNull_false() {
        var length = new Dimension(new BigInteger("4"));
        assertThat(length).isNotEqualTo(null);
    }
}
