package ru.fastdelivery.domain.common.geographic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Distance(BigDecimal distanceMeters) {

    public BigDecimal kilometers() {
        return distanceMeters
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);
    }
}
