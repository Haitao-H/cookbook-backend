package com.harry.service.impl;

import com.harry.mapper.CategoryMapper;
import com.harry.pojo.Category;
import com.harry.service.CategoryService;
import com.harry.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        // cate attribute
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        category.setCreatedUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer createdUser = (Integer) claims.get("id");
        return categoryMapper.list(createdUser);
    }

    @Override
    public Category findById(Integer id) {
        Category category = categoryMapper.findById(id);
        return category;
    }

    @Override
    public void update(Category category) {
        category.setUpdatedAt(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void  deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }


}
