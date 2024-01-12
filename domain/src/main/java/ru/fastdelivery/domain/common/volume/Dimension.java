package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Dimension(BigInteger dimensionMillimeters) {

    private static final BigInteger MAX_DIMENSION = BigInteger.valueOf(1500);

    public Dimension {
        if (isLessThanZero(dimensionMillimeters) || isGreaterThanMaxDimension(dimensionMillimeters)) {
            throw new IllegalArgumentException("Dimensions cannot be below Zero or greater than 1500!");
        }
    }

    public boolean isLessThanZero(BigInteger dimension) {
        return BigInteger.ZERO.compareTo(dimension) > 0;
    }

    public boolean isGreaterThanMaxDimension(BigInteger dimension) {
        return MAX_DIMENSION.compareTo(dimension) < 0;
    }

    public BigDecimal getNormalizedDimension() {
        return normalizeDimension(dimensionMillimeters);
    }

    private BigDecimal normalizeDimension(BigInteger dimension) {
        return new BigDecimal(dimension)
                .divide(new BigDecimal("50"), 0, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("50"));
    }
}
