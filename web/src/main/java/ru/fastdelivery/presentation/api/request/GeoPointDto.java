package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Данные координат")
public record GeoPointDto(

        @Schema(description = "Широта", example = "59.9386")
        @NotNull
        BigDecimal latitude,
        @Schema(description = "Долгота", example = "30.3141")
        @NotNull
        BigDecimal longitude
) {
}
