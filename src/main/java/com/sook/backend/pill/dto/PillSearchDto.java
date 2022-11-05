package com.sook.backend.pill.dto;

import java.util.List;

import com.sook.backend.common.dto.SearchDto;

import lombok.Builder;

public record PillSearchDto(
		String name,
		List<String> attentions
) implements SearchDto {

	@Builder
	public PillSearchDto {
		if (attentions == null) {
			attentions = List.of();
		}
	}
}
