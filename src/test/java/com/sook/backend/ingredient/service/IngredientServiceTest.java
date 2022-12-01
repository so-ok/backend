package com.sook.backend.ingredient.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.AbstractSoOkTest;
import com.sook.backend.ingredient.dto.IngredientDto;

@Transactional
class IngredientServiceTest extends AbstractSoOkTest {

    @Autowired
    private IngredientService ingredientService;

    @Test
    void 영양제_아이디로_총_성분_함량을_가져온다() {
        Map<String, BigDecimal> ingredientSums = ingredientService.getIngredientsSumOf(List.of(95L, 92L));

        assertThat(ingredientSums.get("비타민B2")).isEqualByComparingTo(BigDecimal.valueOf(2.8));
    }

    @Test
    void 영양제_아이디로_영양제_성분의_총량과_기준량을_가져온다() {
        List<IngredientDto> ingredients = ingredientService.getIngredientsOf(List.of(95L, 92L));

        assertThat(ingredients).anySatisfy(ingredientDto -> {
            assertThat(ingredientDto.name()).isEqualTo("비타민B2");
            assertThat(ingredientDto.amount()).isEqualByComparingTo(BigDecimal.valueOf(2.8));
            assertThat(ingredientDto.minimumAmount()).isEqualByComparingTo(BigDecimal.valueOf(0.42));
            assertThat(ingredientDto.maximumAmount()).isEqualByComparingTo(BigDecimal.valueOf(40));
        });
    }

    @Test
    void 기준량이_존재하는_영양제_성분만_표시한다() {
        List<IngredientDto> ingredients = ingredientService.getIngredientsOf(List.of(95L, 92L));

        assertThat(ingredients).noneMatch(ingredientDto -> Objects.isNull(ingredientDto.maximumAmount()));
    }
}