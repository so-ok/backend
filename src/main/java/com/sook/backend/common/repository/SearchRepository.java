package com.sook.backend.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.JPQLQuery;
import com.sook.backend.common.dto.SearchDto;
import com.sook.backend.common.model.BaseModel;

public interface SearchRepository<T extends BaseModel, S extends SearchDto> {
	default Optional<T> searchOne(S search) throws NonUniqueResultException {
		var query = searchQuery(search, null);
		return Optional.ofNullable(query.fetchOne());
	}

	default List<T> search(S search) {
		var query = searchQuery(search, null);
		return query.fetch();
	}

	default Page<T> search(S search, Pageable pageable) {
		var query = searchQuery(search, pageable);
		var queryResult = query.fetchResults();
		return new PageImpl<>(queryResult.getResults(), pageable, queryResult.getTotal());
	}

	JPQLQuery<T> searchQuery(S search, Pageable pageable);
}
