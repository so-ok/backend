package com.sook.backend.pill.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.ingredient.model.Ingredient;
import com.sook.backend.ingredient.model.IngredientUnit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pill_ingredient_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class PillIngredient extends BaseModel {

	@ManyToOne
	private Pill pill;

	@ManyToOne
	private Ingredient ingredient;

	@Column(columnDefinition = "decimal(34, 10)")
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private IngredientUnit unit;
}
