package com.harry.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.harry.mapper.RecipeMapper;
import com.harry.pojo.PageBean;
import com.harry.pojo.Recipes;
import com.harry.service.RecipeService;
import com.harry.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void add(Recipes recipe) {
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> claims = ThreadLocalUtil.get();
        recipe.setUserId((Integer) claims.get("id"));
        recipeMapper.add(recipe);
    }

    @Override
    public PageBean<Recipes> list(Integer pageNo, Integer pageSize, Integer categoryId) {
        PageBean<Recipes> pageBean = new PageBean<>();

        // PageHelper
        PageHelper.startPage(pageNo, pageSize);

        // mapper
        Map<String, Object> claims = ThreadLocalUtil.get();
        List<Recipes> list =  recipeMapper.list(categoryId, (Integer) claims.get("id"));
        Page<Recipes> page = (Page<Recipes>) list;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
