package com.sook.backend.ingredient.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.sum;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.sook.backend.ingredient.dto.IngredientDto;
import com.sook.backend.pill.model.QIngredient;
import com.sook.backend.pill.model.QIngredientDailyStandard;
import com.sook.backend.pill.model.QPillIngredient;

@Repository
public class IngredientRepository extends QuerydslRepositorySupport {

    public IngredientRepository() {
        super(IngredientDto.class);
    }

    public List<IngredientDto> getIngredientsOf(List<Long> pillIds) {
        QPillIngredient qPillIngredient = QPillIngredient.pillIngredient;
        QIngredient qIngredient = QIngredient.ingredient;
        QIngredientDailyStandard qDailyStandard = QIngredientDailyStandard.ingredientDailyStandard;

        return from(qPillIngredient)
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
