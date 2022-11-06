package com.sook.backend.pill.repository;

import com.sook.backend.common.repository.SearchRepository;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.model.Pill;

interface PillSearchRepository extends SearchRepository<Pill, PillSearchDto> {
}
