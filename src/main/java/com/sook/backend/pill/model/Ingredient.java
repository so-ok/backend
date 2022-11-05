package com.sook.backend.pill.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sook.backend.attention.model.Attention;
import com.sook.backend.attention.model.AttentionIngredient;
import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pill_ingredients")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Ingredient extends BaseModel {

	private String name;

	@Enumerated(EnumType.STRING)
	private IngredientUnit defaultUnit;

	@OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
	@Builder.Default
	private List<AttentionIngredient> attentionIngredients = new ArrayList<>();

	/*=====BUSINESS METHODS=====*/
	public List<Attention> attentions() {
		return attentionIngredients.stream()
				.map(AttentionIngredient::attention)
				.toList();
	}
}
