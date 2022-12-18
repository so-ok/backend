package com.sook.backend.pill.dto;

import java.util.List;

import com.sook.backend.attention.model.Attention;
import com.sook.backend.pill.model.Pill;
import com.sook.backend.pill.model.PillIngredient;

import lombok.Builder;

public record PillDto(
        Long id,
        String name,

        String image,
        Integer price,
        List<PillIngredientDto> ingredients,
        List<AttentionPillDto> attentions
) {
    @Builder
    public PillDto {
    }

    public static PillDto of(Pill entity) {
        var ingredients = entity.pillIngredients().stream()
                .map((PillIngredientDto::of))
                .toList();
        var attentions = entity.attentions().stream()
                .map((AttentionPillDto::of))
                .toList();

        return PillDto.builder()
                .id(entity.id())
                .name(entity.name())
                .image(entity.image())
                .price(entity.price())
                .ingredients(ingredients)
                .attentions(attentions)
                .build();
    }

    public record AttentionPillDto(
            String name
    ) {
        @Builder
        public AttentionPillDto {
        }

        public static AttentionPillDto of(Attention entity) {
            return AttentionPillDto.builder()
                    .name(entity.name())
                    .build();
        }
    }

    public record PillIngredientDto(
            String name,
            String amount,
            String unit
    ) {
        @Builder
        public PillIngredientDto {
        }

        public static PillIngredientDto of(PillIngredient entity) {
            return PillIngredientDto.builder()
                    .name(entity.ingredient().name())
                    .amount(entity.amount().toString())
                    .unit(entity.unit().unit())
                    .build();
        }
    }
}
