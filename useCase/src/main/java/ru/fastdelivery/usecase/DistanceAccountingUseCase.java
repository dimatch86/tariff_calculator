package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.geographic.GeoLocationFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class DistanceAccountingUseCase {
    private final VolumeCalculateUseCase volumeCalculatorUseCase;
    private final GeoLocationFactory geoLocationFactory;

    public Price calc(Shipment shipment) {

        var volumeOrWeightBasedPrice = volumeCalculatorUseCase.calc(shipment);
        return volumeOrWeightBasedPrice.multiply(distanceCoefficient(shipment.calculateDistance().kilometers()));
    }
    private BigDecimal distanceCoefficient(BigDecimal distance) {
        return distance.compareTo(geoLocationFactory.baseRateDistance()) > 0 ?
                distance.divide(geoLocationFactory.baseRateDistance(), RoundingMode.UP) :
                BigDecimal.ONE;
    }
}
