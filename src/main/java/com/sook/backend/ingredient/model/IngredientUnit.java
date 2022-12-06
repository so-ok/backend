package com.sook.backend.ingredient.model;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum IngredientUnit {
	CFU("CFU", BigDecimal.valueOf(1)),
	FU("FU", BigDecimal.valueOf(1)),
	GRAM("g", BigDecimal.valueOf(1)),
	MILLI_GRAM("mg", BigDecimal.valueOf(0.001)),
	MICRO_GRAM("μg", BigDecimal.valueOf(0.000001)),
	KCAL("kcal", BigDecimal.valueOf(1)),
	;

	private final String unit;
	private final BigDecimal conversionValue;

	public static BigDecimal calculateConversionValue(IngredientUnit from, IngredientUnit to) {
		if (isConvertible(from) || isConvertible(to)) {
			throw new IllegalArgumentException("Can't convert unit - " + from.name() + " to " + to.name());
		}
		return from.conversionValue.divide(to.conversionValue, MathContext.DECIMAL128);
	}

	private static boolean isConvertible(IngredientUnit unit) {
		return unit != GRAM && unit != MILLI_GRAM && unit != MICRO_GRAM;
	}
}
