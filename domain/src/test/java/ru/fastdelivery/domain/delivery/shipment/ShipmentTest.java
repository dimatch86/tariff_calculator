package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.geographic.GeoPoint;
import ru.fastdelivery.domain.common.geographic.Latitude;
import ru.fastdelivery.domain.common.geographic.Longitude;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var length = new Dimension(BigInteger.valueOf(345));
        var width = new Dimension(BigInteger.valueOf(589));
        var height = new Dimension(BigInteger.valueOf(234));
        var destination = new GeoPoint(new Latitude(BigDecimal.valueOf(55.7522)), new Longitude(BigDecimal.valueOf(37.6156)));
        var departure = new GeoPoint(new Latitude(BigDecimal.valueOf(59.9386)), new Longitude(BigDecimal.valueOf(30.3141)));

        var packages = List.of(new Pack(weight1, length, width, height), new Pack(weight2, length, width, height));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"), destination, departure);

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    void whenSummarizingVolumeOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var length = new Dimension(BigInteger.valueOf(345));
        var width = new Dimension(BigInteger.valueOf(589));
        var height = new Dimension(BigInteger.valueOf(234));
        var destination = new GeoPoint(new Latitude(BigDecimal.valueOf(55.7522)), new Longitude(BigDecimal.valueOf(37.6156)));
        var departure = new GeoPoint(new Latitude(BigDecimal.valueOf(59.9386)), new Longitude(BigDecimal.valueOf(30.3141)));

        var packages = List.of(new Pack(weight1, length, width, height), new Pack(weight2, length, width, height));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"), destination, departure);

        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(volumeOfShipment.volumeCubicMillimeters()).isEqualByComparingTo(BigDecimal.valueOf(105000000));
    }
}