package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.geographic.Distance;
import ru.fastdelivery.domain.common.geographic.GeoPoint;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.util.DistanceCalculator;

import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        GeoPoint destination,
        GeoPoint departure
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public Volume volumeAllPackages() {
        return packages.stream()
                .map(pack -> pack.length().normalizeDimension()
                        .multiply(pack.height().normalizeDimension())
                        .multiply(pack.width().normalizeDimension()))
                .map(Volume::new)
                .reduce(Volume.zero(), Volume::add);
    }

    public Distance calculateDistance() {
        return DistanceCalculator.calculateDistance(destination, departure);
    }
}
