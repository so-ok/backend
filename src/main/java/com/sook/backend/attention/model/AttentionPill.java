package com.sook.backend.attention.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sook.backend.common.model.BaseModel;
import com.sook.backend.pill.model.Pill;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attention_pill_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class AttentionPill extends BaseModel {
	@ManyToOne
	private Attention attention;

	@ManyToOne
	private Pill pill;
}
