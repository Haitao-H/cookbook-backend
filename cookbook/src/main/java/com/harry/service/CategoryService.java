package com.harry.service;

import com.harry.pojo.Category;

import java.util.List;

public interface CategoryService {

    // add new cate
    void add(Category category);

    // list all cate created by the user
    List<Category> list();

    // find by id
    Category findById(Integer id);

    // update category info
    void update(Category category);

    // delete cate by id
    void deleteById(Integer id);
}
