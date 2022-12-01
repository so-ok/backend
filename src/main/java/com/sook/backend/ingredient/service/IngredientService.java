package com.sook.backend.ingredient.service;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.sum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sook.backend.ingredient.dto.IngredientDto;
import com.sook.backend.pill.model.QIngredient;
import com.sook.backend.pill.model.QIngredientDailyStandard;
import com.sook.backend.pill.model.QPillIngredient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getIngredientsSumOf(List<Long> pillIds) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QPillIngredient qPillIngredient = QPillIngredient.pillIngredient;
        QIngredient qIngredient = QIngredient.ingredient;

        return queryFactory.selectFrom(qPillIngredient)
                .leftJoin(qIngredient)
                .on(qPillIngredient.ingredient.eq(qIngredient))
                .where(qPillIngredient.pill.id.in(pillIds))
                .transform(groupBy(qPillIngredient.ingredient.name).as(sum(qPillIngredient.amount)));
    }

    @Transactional(readOnly = true)
    public List<IngredientDto> getIngredientsOf(List<Long> pillIds) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QPillIngredient qPillIngredient = QPillIngredient.pillIngredient;
        QIngredient qIngredient = QIngredient.ingredient;
        QIngredientDailyStandard qDailyStandard = QIngredientDailyStandard.ingredientDailyStandard;

        return queryFactory.selectFrom(qPillIngredient)
                .leftJoin(qIngredient)
                .on(qPillIngredient.ingredient.eq(qIngredient))
                .rightJoin(qDailyStandard)
                .on(qPillIngredient.ingredient.eq(qDailyStandard.ingredient))

                .where(qPillIngredient.pill.id.in(pillIds))
                .transform(groupBy(qPillIngredient.ingredient).as(
                        Projections.constructor(IngredientDto.class, qPillIngredient.ingredient.name,
                                sum(qPillIngredient.amount), qDailyStandard.minimumAmount, qDailyStandard.maximumAmount,
                                qIngredient.defaultUnit)))
                .values().stream().toList();
    }
}
