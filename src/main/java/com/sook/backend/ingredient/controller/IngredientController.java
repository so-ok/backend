package com.sook.backend.ingredient.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.ingredient.dto.IngredientDto;
import com.sook.backend.ingredient.service.IngredientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
@Api(tags = "π μμμ  μ±λΆ")
public class IngredientController {

    private final IngredientService ingredientService;

    @ApiOperation("μμμ λ‘ μ±λΆμ μ΄λκ³Ό κΈ°μ€λμ κ°μ Έμ¨λ€")
    @PostMapping()
    public List<IngredientDto> getIngredients(@RequestBody List<Long> pillIds) {
        return ingredientService.getIngredientsOf(pillIds);
    }
}
