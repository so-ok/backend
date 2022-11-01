package com.sook.backend.food.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.nutrient.model.Nutrient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_nutrient_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FoodNutrient extends BaseModel {

	@ManyToOne
	private Food food;

	@ManyToOne
	private Nutrient nutrient;

	private Double amount;
}
