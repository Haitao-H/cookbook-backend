package com.harry.mapper;

import com.harry.pojo.Recipes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {

    // add new recipe
    @Insert("insert into cookbook.recipes(title, ingredients, instructions, cover_image, user_id, category_id, created_at, updated_at)" +
            " VALUES(#{title}, #{ingredients}, #{instructions}, #{coverImage}, #{userId}, #{categoryId}, #{createdAt}, #{updatedAt})")
    void add(Recipes recipe);

    // list all recipes with pagination
    List<Recipes> list(Integer categoryId, Integer userId);
}
