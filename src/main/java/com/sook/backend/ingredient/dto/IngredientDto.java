package com.sook.backend.ingredient.dto;

import java.math.BigDecimal;

import com.sook.backend.pill.model.IngredientUnit;

public record IngredientDto(
        String name,
        BigDecimal amount,
        BigDecimal minimumAmount,
        BigDecimal maximumAmount,
        String unit
) {
    public IngredientDto {
    }

    public IngredientDto(String name, BigDecimal amount, BigDecimal minimumAmount, BigDecimal maximumAmount,
            IngredientUnit unit) {
        this(name, amount, minimumAmount, maximumAmount, unit.unit());
    }
}
