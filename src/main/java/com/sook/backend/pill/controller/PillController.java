package com.sook.backend.pill.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.common.annotations.NoApiAuth;
import com.sook.backend.pill.dto.PillDto;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.service.PillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pill")
@Api(tags = "💊 영양제")
public class PillController {
	private final PillService pillService;

	@NoApiAuth
	@ApiOperation("id로 가져오기")
	@GetMapping("/{id}")
	public ResponseEntity<PillDto> getPill(@PathVariable("id") Long id) {
		PillDto pill = pillService.findById(id);
		return ResponseEntity.ok(pill);
	}

	@NoApiAuth
	@ApiOperation("영양제 검색")
	@PostMapping("/search")
	public ResponseEntity<Page<PillDto>> search(@RequestBody PillSearchDto searchDto, Pageable pageable) {
		Page<PillDto> pillDtos = pillService.search(searchDto, pageable);
		return ResponseEntity.ok(pillDtos);
	}
}
