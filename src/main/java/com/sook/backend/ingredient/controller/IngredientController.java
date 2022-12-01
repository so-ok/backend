package com.sook.backend.ingredient.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.ingredient.dto.IngredientDto;
import com.sook.backend.ingredient.dto.IngredientRequestDto;
import com.sook.backend.ingredient.service.IngredientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
@Api(tags = "📊 영양제 성분")
public class IngredientController {

    private final IngredientService ingredientService;

    @ApiOperation("영양제로 성분의 총량과 기준량을 가져온다")
    @PostMapping("ingredients")
    public List<IngredientDto> getIngredients(@RequestBody IngredientRequestDto ingredientRequestDto) {
        return ingredientService.getIngredientsOf(ingredientRequestDto.pillIds());
    }
}
