package com.sook.backend.pill.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pill")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Pill extends BaseModel {

	private String name;

	@OneToMany(mappedBy = "pill")
	private List<PillIngredient> pillIngredients = new ArrayList<>();

	/*=====BUSINESS METHODS=====*/
	public List<Ingredient> ingredients() {
		return pillIngredients.stream()
			.map(PillIngredient::ingredient)
			.toList();
	}
}
