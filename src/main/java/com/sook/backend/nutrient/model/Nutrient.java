package com.sook.backend.nutrient.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nutrient")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Nutrient extends BaseModel {

	private String name;
}
