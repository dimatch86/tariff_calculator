package ru.fastdelivery.domain.common.volume;

import ru.fastdelivery.domain.common.price.Price;

import java.math.BigInteger;

public interface VolumePriceProvider {

    Price costPerCubicMeter();
    boolean isValidDimension(BigInteger dimensionValue);
    BigInteger maxDimension();
}
