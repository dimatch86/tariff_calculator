package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.geographic.GeoLocationFactory;
import ru.fastdelivery.domain.common.geographic.GeoPoint;
import ru.fastdelivery.domain.common.volume.DimensionFactory;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.DistanceAccountingUseCase;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final DistanceAccountingUseCase distanceAccountingUseCase;
    private final CurrencyFactory currencyFactory;
    private final GeoLocationFactory geoLocationFactory;
    private final DimensionFactory dimensionFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {

        var packsToCalculate = request.packages().stream()
                .map(cargoPackage ->
                        new Pack(new Weight(cargoPackage.weight()),
                        dimensionFactory.create(cargoPackage.length()),
                        dimensionFactory.create(cargoPackage.width()),
                        dimensionFactory.create(cargoPackage.height())))
                .toList();
        var departure = new GeoPoint(geoLocationFactory.createLatitude(request.departure().latitude()) , geoLocationFactory.createLongitude(request.departure().longitude()));
        var destination = new GeoPoint(geoLocationFactory.createLatitude(request.destination().latitude()) , geoLocationFactory.createLongitude(request.destination().longitude()));

        var shipment = new Shipment(packsToCalculate,
                currencyFactory.create(request.currencyCode()), departure, destination);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        var priceWithDistance = distanceAccountingUseCase.calc(shipment);
        return new CalculatePackagesResponse(priceWithDistance, minimalPrice);
    }
}

