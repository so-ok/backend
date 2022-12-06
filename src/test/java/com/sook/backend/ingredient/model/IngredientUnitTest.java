package com.sook.backend.ingredient.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class IngredientUnitTest {

    @Test
    @DisplayName("단위변환 테스트(Gram to MilliGram)")
    public void testConvertGramToMilliGram() throws Exception {
        //given
        IngredientUnit from = IngredientUnit.GRAM;
        IngredientUnit to = IngredientUnit.MILLI_GRAM;

        //when
        BigDecimal conversionValue = IngredientUnit.calculateConversionValue(from, to);

        //then
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(conversionValue));
    }

    @Test
    @DisplayName("단위변환 테스트(Gram to MicroGram)")
    public void testConvertGramToMicroGram() throws Exception {
        //given
        IngredientUnit from = IngredientUnit.GRAM;
        IngredientUnit to = IngredientUnit.MICRO_GRAM;

        //when
        BigDecimal conversionValue = IngredientUnit.calculateConversionValue(from, to);

        //then
        assertEquals(0, BigDecimal.valueOf(1000000).compareTo(conversionValue));
    }

    @Test
    @DisplayName("단위변환 실패 테스트(CFU to milligram)")
    public void failConvertCFUToMilligram() throws Exception {
        //given
        IngredientUnit from = IngredientUnit.CFU;
        IngredientUnit to = IngredientUnit.MILLI_GRAM;

        //when
        Executable expectFail = () -> IngredientUnit.calculateConversionValue(from, to);

        //then
        assertThrows(IllegalArgumentException.class, expectFail);
    }
}
