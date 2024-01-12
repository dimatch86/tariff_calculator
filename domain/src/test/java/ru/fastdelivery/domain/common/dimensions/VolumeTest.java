package ru.fastdelivery.domain.common.dimensions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.domain.common.volume.Volume;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VolumeTest {

    @Test
    @DisplayName("Добавление положительного объема -> объем увеличился")
    void whenAddPositiveVolume_thenVolumeIsIncreased() {
        var volumeBase = new Volume(new BigDecimal("1000"));
        var actual = volumeBase.add(new Volume(new BigDecimal("1000")));

        assertThat(actual)
                .isEqualTo(new Volume(new BigDecimal("2000")));
    }

    @Test
    @DisplayName("Первый объем больше второго -> true")
    void whenFirstVolumeGreaterThanSecond_thenTrue() {
        var volumeBig = new Volume(new BigDecimal("1001"));
        var volumeSmall = new Volume(new BigDecimal("1000"));

        assertThat(volumeBig.greaterThan(volumeSmall)).isTrue();
    }

    @Test
    void equalsTypeVolume_same() {
        var volume = new Volume(new BigDecimal("1000"));
        var volumeSame = new Volume(new BigDecimal("1000"));

        assertThat(volume)
                .isEqualTo(volumeSame)
                .hasSameHashCodeAs(volumeSame);
    }

    @Test
    void equalsNull_false() {
        var volume = new Volume(new BigDecimal("45"));

        assertThat(volume).isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ "1000, 1, -1",
            "199, 199, 0",
            "50, 999, 1" })
    void compareToTest(BigDecimal high, BigDecimal low, int expected) {
        var volumeLow = new Volume(low);
        var volumeHigh = new Volume(high);

        assertThat(volumeLow.compareTo(volumeHigh))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Запрос на перевод в метры кубические -> получено корректное значение")
    void whenGetKilograms_thenReceiveKg() {
        var volume = new Volume(new BigDecimal("163625000"));

        var actual = volume.cubicMeters();

        assertThat(actual).isEqualByComparingTo(new BigDecimal("0.1636"));
    }
}
