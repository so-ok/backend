package com.sook.backend.pill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sook.backend.pill.model.Pill;

public interface PillRepository extends JpaRepository<Pill, Long>, PillSearchRepository {
}
