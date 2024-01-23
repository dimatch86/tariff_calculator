package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@RequiredArgsConstructor
public class VolumeCalculateUseCase {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment) {
        var volumeAllPackagesKg = shipment.volumeAllPackages().cubicMeters();
        var weightBasedPrice = tariffCalculateUseCase.calc(shipment);

        return volumePriceProvider
                .costPerCubicMeter()
                .multiply(volumeAllPackagesKg)
                .max(weightBasedPrice);
    }
}
