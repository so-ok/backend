package com.sook.backend.ingredient.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.ingredient.dto.IngredientDto;
import com.sook.backend.ingredient.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<IngredientDto> getIngredientsOf(List<Long> pillIds) {
        return ingredientRepository.getIngredientsOf(pillIds);
    }
}
