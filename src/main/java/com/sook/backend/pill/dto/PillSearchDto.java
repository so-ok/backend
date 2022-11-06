package com.sook.backend.pill.dto;

import java.util.List;

import com.sook.backend.common.dto.SearchDto;

import lombok.Builder;

public record PillSearchDto(
		Long id,
		String name,
		List<String> attentions,
		List<String> ingredients
) implements SearchDto {

	@Builder
	public PillSearchDto {
		if (attentions == null) {
			attentions = List.of();
		}
		if (ingredients == null) {
			ingredients = List.of();
		}
	}
}
