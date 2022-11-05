package com.sook.backend.pill.repository;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;
import com.sook.backend.attention.model.AttentionPill;
import com.sook.backend.attention.model.QAttention;
import com.sook.backend.attention.model.QAttentionPill;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.model.Pill;
import com.sook.backend.pill.model.PillIngredient;
import com.sook.backend.pill.model.QIngredient;
import com.sook.backend.pill.model.QPill;
import com.sook.backend.pill.model.QPillIngredient;

public class PillSearchRepositoryImpl
		extends QuerydslRepositorySupport
		implements PillSearchRepository {

	public PillSearchRepositoryImpl() {
		super(Pill.class);
	}

	@Override
	public JPQLQuery<Pill> searchQuery(PillSearchDto search, Pageable pageable) {
		QPill qPill = QPill.pill;
		JPQLQuery<Pill> query = from(qPill);

		if (search.name() != null) {
			query.where(
					qPill.name.contains(search.name())
			);
		}

		if (search.attentions().size() > 0) {
			QAttentionPill qAttentionPill = QAttentionPill.attentionPill;
			QAttention qAttention = QAttention.attention;

			var filteredAttentions = search.attentions().stream()
					.filter(Objects::nonNull)
					.toList();

			JPQLQuery<AttentionPill> attentionPillQuery = from(qAttentionPill)
					.join(qAttention).on(qAttentionPill.attention.id.eq(qAttention.id))
					.where(qAttention.name.in(filteredAttentions));

			JPQLQuery<Long> pillIds = attentionPillQuery.select(qAttentionPill.pill.id);

			query.where(
					qPill.id.in(pillIds)
			);
		}

		if (search.ingredients().size() > 0) {
			QPillIngredient qPillIngredient = QPillIngredient.pillIngredient;
			QIngredient qIngredient = QIngredient.ingredient;

			var filteredIngredients = search.ingredients().stream()
					.filter(Objects::nonNull)
					.toList();

			JPQLQuery<PillIngredient> pillIngredientQuery = from(qPillIngredient)
					.join(qIngredient).on(qPillIngredient.ingredient.id.eq(qIngredient.id))
					.where(qIngredient.name.in(filteredIngredients));

			JPQLQuery<Long> pillIds = pillIngredientQuery.select(qPillIngredient.pill.id);

			query.where(
					qPill.id.in(pillIds)
			);
		}

		return query;
	}
}
