package com.sook.backend.attention.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.pill.model.Ingredient;
import com.sook.backend.pill.model.Pill;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attentions", indexes = {
		@Index(name = "attentions_name_uindex", columnList = "name", unique = true)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Attention extends BaseModel {
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "attention", fetch = FetchType.LAZY)
	@Builder.Default
	private List<AttentionIngredient> attentionIngredients = new ArrayList<>();

	@OneToMany(mappedBy = "attention", fetch = FetchType.LAZY)
	@Builder.Default
	private List<AttentionPill> attentionPills = new ArrayList<>();

	/*=====BUSINESS METHODS=====*/
	public List<Ingredient> ingredients() {
		return attentionIngredients.stream()
				.map(AttentionIngredient::ingredient)
				.toList();
	}

	public List<Pill> pills() {
		return attentionPills.stream()
				.map(AttentionPill::pill)
				.toList();
	}
}
