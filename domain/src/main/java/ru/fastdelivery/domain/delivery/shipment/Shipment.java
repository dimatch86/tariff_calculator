package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public Volume volumeAllPackages() {
        return packages.stream()
                .map(pack -> pack.length().getNormalizedDimension()
                        .multiply(pack.height().getNormalizedDimension())
                        .multiply(pack.width().getNormalizedDimension()))
                .map(Volume::new)
                .reduce(Volume.zero(), Volume::add);
    }
}
