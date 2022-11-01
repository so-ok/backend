package com.sook.backend.nutrient.model;

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
@Table(name = "nutrient_attention_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class NutrientEffect extends BaseModel {

	@ManyToOne
	private Nutrient nutrient;

	@ManyToOne
	private Effector effector;
}
