package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;

import java.math.BigDecimal;
import java.math.BigInteger;

@ConfigurationProperties("volume")
@Setter
public class VolumePriceProperties implements VolumePriceProvider {

    private BigDecimal perCubicMeter;
    private BigInteger maxDimension;

    @Autowired
    private CurrencyFactory currencyFactory;


    @Override
    public Price costPerCubicMeter() {
        return new Price(perCubicMeter, currencyFactory.create("RUB"));
    }

    @Override
    public boolean isValidDimension(BigInteger dimensionValue) {
        return dimensionValue.compareTo(maxDimension) <= 0;
    }

    @Override
    public BigInteger maxDimension() {
        return maxDimension;
    }
}
