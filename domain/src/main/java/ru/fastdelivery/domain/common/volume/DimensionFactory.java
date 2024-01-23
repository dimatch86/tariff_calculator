package ru.fastdelivery.domain.common.volume;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.text.MessageFormat;

@RequiredArgsConstructor
public class DimensionFactory {

    private final VolumePriceProvider volumePriceProvider;

    public Dimension create(BigInteger dimensionMillimeters) {

        if (dimensionMillimeters == null || !volumePriceProvider.isValidDimension(dimensionMillimeters)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Dimension has not valid value. It must be greater then zero " +
                            "and below {0}", volumePriceProvider.maxDimension())
            );
        }
        return new Dimension(dimensionMillimeters);
    }
}
