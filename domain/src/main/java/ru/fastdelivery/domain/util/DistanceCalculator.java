package ru.fastdelivery.domain.util;

import lombok.experimental.UtilityClass;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import ru.fastdelivery.domain.common.geographic.Distance;
import ru.fastdelivery.domain.common.geographic.GeoPoint;

import java.math.BigDecimal;

@UtilityClass
public class DistanceCalculator {


    private final GeodeticCalculator geodeticCalculator =
            new GeodeticCalculator(DefaultGeographicCRS.WGS84);

    public Distance calculateDistance(GeoPoint destination, GeoPoint departure) {
        geodeticCalculator.setStartingGeographicPoint(departure.longitude().longitudeValue().doubleValue(), departure.latitude().latitudeValue().doubleValue());
        geodeticCalculator.setDestinationGeographicPoint(destination.longitude().longitudeValue().doubleValue(), destination.latitude().latitudeValue().doubleValue());
        return new Distance(BigDecimal.valueOf(geodeticCalculator.getOrthodromicDistance()));
    }
}
