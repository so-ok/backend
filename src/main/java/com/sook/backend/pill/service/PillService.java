package com.sook.backend.pill.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.pill.dto.PillDto;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.model.Pill;
import com.sook.backend.pill.repository.PillRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PillService {
    private final PillRepository pillRepository;

    public PillDto findById(Long id) {
        Pill pill = pillRepository.findById(id)
                .orElseThrow();
        return PillDto.of(pill);
    }

    public List<PillDto> findByIds(List<Long> ids) {
        List<Pill> pills = pillRepository.findAllById(ids);
        List<PillDto> pillDtos = pills.stream()
                .map(PillDto::of)
                .toList();
        return pillDtos;
    }

    public PillDto searchOne(PillSearchDto searchDto) {
        Pill pill = pillRepository.searchOne(searchDto).orElseThrow();
        return PillDto.of(pill);
    }

    public Page<PillDto> search(PillSearchDto searchDto, Pageable pageable) {
        return pillRepository.search(searchDto, pageable).map(PillDto::of);
    }
}
