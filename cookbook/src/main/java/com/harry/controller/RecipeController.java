package com.harry.controller;

import com.harry.pojo.PageBean;
import com.harry.pojo.Recipes;
import com.harry.pojo.Result;
import com.harry.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public Result add(@RequestBody @Validated Recipes recipe) {
        recipeService.add(recipe);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Recipes>> list(
            Integer pageNo, Integer pageSize,
            @RequestParam(required = false) Integer categoryId) {
        PageBean<Recipes> pageBean = recipeService.list(pageNo, pageSize, categoryId);
        return Result.success(pageBean);
    }

}
