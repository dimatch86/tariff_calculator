package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.geographic.GeoLocationFactory;
import ru.fastdelivery.domain.common.geographic.GeoLocationProvider;
import ru.fastdelivery.domain.common.volume.DimensionFactory;
import ru.fastdelivery.domain.common.volume.VolumePriceProvider;
import ru.fastdelivery.usecase.DistanceAccountingUseCase;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumeCalculateUseCase;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class AppConfiguration {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider) {
        return new TariffCalculateUseCase(weightPriceProvider);
    }

    @Bean
    public DimensionFactory dimensionFactory(VolumePriceProvider volumePriceProvider) {
        return new DimensionFactory(volumePriceProvider);
    }

    @Bean
    public VolumeCalculateUseCase volumeCalculator(TariffCalculateUseCase tariffCalculateUseCase, VolumePriceProvider volumePriceProvider) {
        return new VolumeCalculateUseCase(tariffCalculateUseCase, volumePriceProvider);
    }
    @Bean
    public GeoLocationFactory distanceFactory(GeoLocationProvider geoLocationProperties) {
        return new GeoLocationFactory(geoLocationProperties);
    }

    @Bean
    public DistanceAccountingUseCase coordinateCalculator(VolumeCalculateUseCase volumeCalculateUseCase, GeoLocationFactory geoLocationFactory) {
        return new DistanceAccountingUseCase(volumeCalculateUseCase, geoLocationFactory);
    }
}
