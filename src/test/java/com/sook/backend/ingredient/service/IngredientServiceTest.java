package com.sook.backend.ingredient.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.AbstractSoOkTest;
import com.sook.backend.ingredient.dto.IngredientDto;

@Transactional
class IngredientServiceTest extends AbstractSoOkTest {

    private static final List<Long> TEST_PILL_IDS = List.of(7L, 111L, 195L, 89L, 135L, 95L, 174L, 132L, 82L, 149L,
            1671L, 51L, 896L);

    @Autowired
    private IngredientService ingredientService;

    @Test
    void 영양제_아이디로_영양제_성분의_총량과_기준량을_가져온다() {
        List<IngredientDto> ingredients = ingredientService.getIngredientsOf(TEST_PILL_IDS);

        assertThat(ingredients).anySatisfy(ingredientDto -> {
            assertThat(ingredientDto.name()).isEqualTo("비타민B1");
            assertThat(ingredientDto.amount()).isEqualByComparingTo(BigDecimal.valueOf(17.08));
            assertThat(ingredientDto.maximumAmount()).isEqualByComparingTo(BigDecimal.valueOf(100.0));
            assertThat(ingredientDto.minimumAmount()).isEqualByComparingTo(BigDecimal.valueOf(0.36));
        });
    }

    @Test
    void 기준량이_존재하는_영양제_성분만_표시한다() {
        List<IngredientDto> ingredients = ingredientService.getIngredientsOf(TEST_PILL_IDS);

        assertThat(ingredients).noneMatch(ingredientDto -> Objects.isNull(ingredientDto.maximumAmount()));
    }
}