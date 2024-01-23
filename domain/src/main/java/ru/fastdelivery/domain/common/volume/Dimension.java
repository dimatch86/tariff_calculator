package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Dimension(BigInteger dimensionMillimeters) {

    public Dimension {
        if (isLessThanZero(dimensionMillimeters)) {
            throw new IllegalArgumentException("Dimensions cannot be below Zero!");
        }
    }

    public boolean isLessThanZero(BigInteger dimension) {
        return BigInteger.ZERO.compareTo(dimension) > 0;
    }

    public BigDecimal normalizeDimension() {
        return new BigDecimal(dimensionMillimeters)
                .divide(new BigDecimal("50"), 0, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("50"));
    }
}
