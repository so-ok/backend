package com.sook.backend.pill.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient_daily_standard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class IngredientDailyStandard extends BaseModel {

	@ManyToOne(optional = false)
	private Ingredient ingredient;

	@Column(columnDefinition = "decimal(34, 10)", nullable = false)
	private BigDecimal minimumAmount;

	@Column(columnDefinition = "decimal(34, 10)", nullable = false)
	private BigDecimal maximumAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private IngredientUnit unit;
}
