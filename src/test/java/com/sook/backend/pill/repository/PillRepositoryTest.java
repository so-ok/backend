package com.sook.backend.pill.repository;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.AbstractSoOkTest;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.model.Pill;

@Transactional
@Disabled
class PillRepositoryTest extends AbstractSoOkTest {

    @Autowired
    PillRepository pillRepository;

    @Test
    @DisplayName("search() 테스트 - name")
    public void testSearchByName() throws Exception {
        //given
        PillSearchDto search = PillSearchDto.builder()
                .name("비오틴")
                .build();

        //when
        List<Pill> pills = pillRepository.search(search);

        //then
        assertEquals(27, pills.size());
        pills.forEach((pill -> {
            assertTrue(pill.name().contains(search.name()));
        }));
    }

    @Test
    @DisplayName("search() 테스트 - attentions")
    public void testSearchByAttention() throws Exception {
        //given
        List<String> attentions = List.of("장 건강", "간 건강");
        PillSearchDto search = PillSearchDto.builder()
                .attentions(attentions)
                .build();

        //when
        List<Pill> pills = pillRepository.search(search);

        //then
        pills.forEach((pill -> {
            pill.attentionPills().forEach((attentionPill -> {
                anyOf(search.attentions()
                        .stream()
                        .map(attention -> attentionPill.attention().name().contains(attention))
                        .toList());
            }));
        }));

        assertEquals(1141, pills.size());
    }

    @Test
    @DisplayName("search() 테스트 - attentions, ingredients")
    public void testSearchByAttentionAndIngredients() throws Exception {
        //given
        List<String> attentions = List.of("눈 건강", "간 건강");
        List<String> ingredients = List.of("비타민A");

        PillSearchDto search = PillSearchDto.builder()
                .attentions(attentions)
                .ingredients(ingredients)
                .build();

        //when
        List<Pill> pills = pillRepository.search(search);

        //then`
        pills.forEach((pill -> {
            pill.attentionPills().forEach((attentionPill -> {
                anyOf(search.attentions()
                        .stream()
                        .map(attention -> attentionPill.attention().name().contains(attention))
                        .toList());
            }));
            pill.ingredients().forEach((pillIngredient -> {
                anyOf(search.ingredients()
                        .stream()
                        .map(ingredient -> pillIngredient.name().contains(ingredient))
                        .toList());
            }));
        }));

        assertEquals(195, pills.size());
    }

    @Test
    @DisplayName("search() with Paging 테스트 - attentions, ingredients")
    public void testSearchByAttentionAndIngredientsPageable() throws Exception {
        //given
        List<String> attentions = List.of("눈 건강", "간 건강");
        List<String> ingredients = List.of("비타민A");

        PillSearchDto search = PillSearchDto.builder()
                .attentions(attentions)
                .ingredients(ingredients)
                .build();

        //when
        Page<Pill> pills = pillRepository.search(search, PageRequest.of(0, 30));

        //then
        pills.forEach((pill -> {
            pill.attentionPills().forEach((attentionPill -> {
                anyOf(search.attentions()
                        .stream()
                        .map(attention -> attentionPill.attention().name().contains(attention))
                        .toList());
            }));
            pill.ingredients().forEach((pillIngredient -> {
                anyOf(search.ingredients()
                        .stream()
                        .map(ingredient -> pillIngredient.name().contains(ingredient))
                        .toList());
            }));
        }));

        assertEquals(195, pills.getTotalElements());
    }
}
