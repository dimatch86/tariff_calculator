package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056.45}]")
        @NotNull
        @NotEmpty(message = "The cargo must contain a package")
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull(message = "The shipment must contain currency")
        String currencyCode,
        @Schema(description = "Координаты пункта назначения")
        @NotNull(message = "The shipment must contain destination")
        GeoPointDto destination,
        @Schema(description = "Координаты пункта отправления")
        @NotNull(message = "The shipment must contain departure")
        GeoPointDto departure

) {
}
