package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.geographic.*;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.domain.util.DistanceCalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DistanceAccountingUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final GeoLocationFactory geoLocationFactory = mock(GeoLocationFactory.class);
    final MockedStatic<DistanceCalculator> utilsMockedStatic = Mockito.mockStatic(DistanceCalculator.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");
    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider);
    final VolumeCalculateUseCase volumeCalculateUseCase = new VolumeCalculateUseCase(tariffCalculateUseCase, volumePriceProvider);
    final DistanceAccountingUseCase distanceAccountingUseCase = new DistanceAccountingUseCase(volumeCalculateUseCase, geoLocationFactory);
    static final BigDecimal BASE_RATE_DISTANCE_KILOMETERS = BigDecimal.valueOf(450);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerCubicMeter = new Price(BigDecimal.valueOf(10000), currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var departure = new GeoPoint(new Latitude(BigDecimal.valueOf(59.9386)), new Longitude(BigDecimal.valueOf(30.3141)));
        var destination = new GeoPoint(new Latitude(BigDecimal.valueOf(55.7522)), new Longitude(BigDecimal.valueOf(37.6156)));
        var distanceGreaterThenBaseDistance = new Distance(BigDecimal.valueOf(636019));
        var distanceLessThenBaseDistance = new Distance(BigDecimal.valueOf(235148));

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.costPerCubicMeter()).thenReturn(pricePerCubicMeter);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(geoLocationFactory.baseRateDistance()).thenReturn(BASE_RATE_DISTANCE_KILOMETERS);


        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)),
                new Dimension(BigInteger.valueOf(345)),
                new Dimension(BigInteger.valueOf(589)),
                new Dimension(BigInteger.valueOf(234)))),
                new CurrencyFactory(code -> true).create("RUB"),
                destination,
                departure);


        var expectedPriceWhenDistanceLessThanBased = new Price(BigDecimal.valueOf(525), currency);
        var expectedPriceWhenDistanceGreaterThanBased = new Price(BigDecimal.valueOf(742.35), currency);

        utilsMockedStatic.when(() -> DistanceCalculator.calculateDistance(destination, departure)).thenReturn(distanceGreaterThenBaseDistance);
        var actualPriceWhenDistanceGreaterThanBased = distanceAccountingUseCase.calc(shipment);

        assertThat(actualPriceWhenDistanceGreaterThanBased).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPriceWhenDistanceGreaterThanBased);

        utilsMockedStatic.when(() -> DistanceCalculator.calculateDistance(destination, departure)).thenReturn(distanceLessThenBaseDistance);
        var actualPriceWhenDistanceLessThanBased = distanceAccountingUseCase.calc(shipment);

        assertThat(actualPriceWhenDistanceLessThanBased).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPriceWhenDistanceLessThanBased);
    }
}
