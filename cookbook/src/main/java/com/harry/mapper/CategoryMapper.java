package com.harry.mapper;

import com.harry.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // create
    @Insert("insert into cookbook.category(name, description, created_at, updated_at, created_user)" +
            "values(#{name}, #{description}, #{createdAt}, #{updatedAt}, #{createdUser})")
    void add(Category category);

    // get all cate created by the user
    @Select("select * from cookbook.category where created_user=#{createdUser}")
    List<Category> list(Integer createdUser);

    // find by id
    @Select("select * from cookbook.category where id=#{id}")
    Category findById(Integer id);

    // update category info
    @Update("update cookbook.category set name=#{name}, description=#{description}, updated_at=#{updatedAt} where id=#{id}")
    void update(Category category);

    // delete category by id
    @Delete("delete from cookbook.category where id=#{id}")
    void deleteById(Integer id);
}
