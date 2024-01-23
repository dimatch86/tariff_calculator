package ru.fastdelivery.domain.common.dimensions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.volume.DimensionFactory;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DimensionFactoryTest {
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    DimensionFactory factory = new DimensionFactory(volumePriceProvider);
    static final BigInteger VALID_DIMENSION = BigInteger.valueOf(850);
    static final BigInteger INVALID_DIMENSION = BigInteger.valueOf(1550);

    @Test
    @DisplayName("Попытка создать объект Dimension -> получен корректный объект")
    void whenLengthLessOrEqualsMaxLength_thenReturnsCorrectDimension() {

        var length = VALID_DIMENSION;
        when(volumePriceProvider.isValidDimension(length)).thenReturn(true);
        var actualDimension = factory.create(length);
        assertThat(actualDimension).isNotEqualTo(null);
        assertThat(actualDimension.dimensionMillimeters()).isEqualByComparingTo(length);
    }

    @Test
    @DisplayName("Попытка передать габарит больше максимального -> исключение")
    void whenLengthMoreThanMaxLength_thenThrowException() {

        var length = INVALID_DIMENSION;
        when(volumePriceProvider.isValidDimension(length)).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(length));
    }
}
