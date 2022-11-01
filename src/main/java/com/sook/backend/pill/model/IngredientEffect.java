package com.sook.backend.pill.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.attention.model.Effector;
import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pill_ingredient_effect_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class IngredientEffect extends BaseModel {

	@ManyToOne
	private Ingredient ingredient;

	@ManyToOne
	private Effector effector;
}
