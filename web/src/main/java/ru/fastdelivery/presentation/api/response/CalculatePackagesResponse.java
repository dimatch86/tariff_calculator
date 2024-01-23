package ru.fastdelivery.presentation.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.domain.common.price.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Schema(description = "Модель данных ответа")
public record CalculatePackagesResponse (
        @Schema(description = "Расчетная стоимость доставки", example = "1187.76")
        BigDecimal totalPrice,
        @Schema(description = "Минимальная стоимость доставки", example = "350")
        BigDecimal minimalPrice,
        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        String currencyCode
) {
    public CalculatePackagesResponse(Price totalPrice, Price minimalPrice) {
        this(totalPrice.amount().setScale(2, RoundingMode.UP), minimalPrice.amount(), totalPrice.currency().getCode());

        if (currencyIsNotEqual(totalPrice, minimalPrice)) {
            throw new IllegalArgumentException("Currency codes must be the same");
        }
    }

    private static boolean currencyIsNotEqual(Price priceLeft, Price priceRight) {
        return !priceLeft.currency().equals(priceRight.currency());
    }
}
