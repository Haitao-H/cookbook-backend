package com.harry.service;

import com.harry.pojo.PageBean;
import com.harry.pojo.Recipes;

public interface RecipeService {

    // add new recipe
    void add(Recipes recipe);

    // list all recipes with pagination
    PageBean<Recipes> list(Integer pageNo, Integer pageSize, Integer categoryId);
}
