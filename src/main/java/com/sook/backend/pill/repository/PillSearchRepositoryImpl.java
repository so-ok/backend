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
import com.sook.backend.pill.model.QPill;

public class PillSearchRepositoryImpl
		extends QuerydslRepositorySupport
		implements PillSearchRepository {

	public PillSearchRepositoryImpl() {
		super(Pill.class);
	}

	@Override
	public JPQLQuery<Pill> searchQuery(PillSearchDto search, Pageable pageable) {
		QPill qPill = QPill.pill;
		QAttentionPill qAttentionPill = QAttentionPill.attentionPill;
		QAttention qAttention = QAttention.attention;

		JPQLQuery<Pill> query = from(qPill);

		if (search.name() != null) {
			query.where(
					qPill.name.contains(search.name())
			);
		}

		if (search.attentions().size() > 0) {
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

		return query;
	}
}
