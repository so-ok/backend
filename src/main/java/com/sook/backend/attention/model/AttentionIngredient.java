package com.sook.backend.attention.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.pill.model.Ingredient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attention_ingredient_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class AttentionIngredient extends BaseModel {
	@ManyToOne
	private Attention attention;

	@ManyToOne
	private Ingredient ingredient;
}
