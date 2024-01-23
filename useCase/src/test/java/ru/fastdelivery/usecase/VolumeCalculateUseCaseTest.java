package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.geographic.GeoPoint;
import ru.fastdelivery.domain.common.geographic.Latitude;
import ru.fastdelivery.domain.common.geographic.Longitude;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VolumeCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider);
    final VolumeCalculateUseCase volumeCalculateUseCase = new VolumeCalculateUseCase(tariffCalculateUseCase, volumePriceProvider);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerCubicMeter = new Price(BigDecimal.valueOf(10000), currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.costPerCubicMeter()).thenReturn(pricePerCubicMeter);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)),
                new Dimension(BigInteger.valueOf(345)),
                new Dimension(BigInteger.valueOf(589)),
                new Dimension(BigInteger.valueOf(234)))),
                new CurrencyFactory(code -> true).create("RUB"),
                new GeoPoint(new Latitude(BigDecimal.valueOf(59.9386)), new Longitude(BigDecimal.valueOf(30.3141))),
                new GeoPoint(new Latitude(BigDecimal.valueOf(55.7522)), new Longitude(BigDecimal.valueOf(37.6156))));

        var expectedPrice = new Price(BigDecimal.valueOf(525), currency);

        var weightBasedPrice = tariffCalculateUseCase.calc(shipment);
        var actualPrice = volumeCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
        assertThat(actualPrice.max(weightBasedPrice)).isEqualTo(actualPrice);
    }
}
