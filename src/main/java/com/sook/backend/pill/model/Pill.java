package com.sook.backend.pill.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sook.backend.attention.model.Attention;
import com.sook.backend.attention.model.AttentionPill;
import com.sook.backend.common.model.BaseModel;
import com.sook.backend.ingredient.model.Ingredient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pills")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Pill extends BaseModel {

    private String name;

    @OneToMany(mappedBy = "pill", fetch = FetchType.LAZY)
    @Builder.Default
    private List<PillIngredient> pillIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "pill", fetch = FetchType.LAZY)
    @Builder.Default
    private List<AttentionPill> attentionPills = new ArrayList<>();

    @Lob
    private String image;

    private Integer price;

    public List<Ingredient> ingredients() {
        return pillIngredients.stream()
                .map(PillIngredient::ingredient)
                .toList();
    }

    public List<Attention> attentions() {
        return attentionPills.stream()
                .map(AttentionPill::attention)
                .toList();
    }
}
