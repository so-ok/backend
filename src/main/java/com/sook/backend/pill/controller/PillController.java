package com.sook.backend.pill.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.pill.dto.PillDto;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.service.PillService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pill")
public class PillController {
	private final PillService pillService;

	@GetMapping("/{id}")
	public ResponseEntity<PillDto> getPill(@PathVariable("id") Long id) {
		PillSearchDto search = PillSearchDto.builder()
				.id(id)
				.build();
		PillDto pill = pillService.searchOne(search);
		return ResponseEntity.ok(pill);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<PillDto>> search(@RequestBody PillSearchDto searchDto, Pageable pageable) {
		Page<PillDto> pillDtos = pillService.search(searchDto, pageable);
		return ResponseEntity.ok(pillDtos);
	}
}
