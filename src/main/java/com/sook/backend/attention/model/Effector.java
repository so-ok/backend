package com.sook.backend.attention.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.nutrient.model.NutrientEffect;
import com.sook.backend.pill.model.IngredientEffect;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attention_effector")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Effector extends BaseModel {

	@OneToOne
	private Attention attention;

	@OneToMany(mappedBy = "effector")
	private List<NutrientEffect> nutrientEffectors;

	@OneToMany(mappedBy = "effector")
	private List<IngredientEffect> ingredientEffectors;
}
