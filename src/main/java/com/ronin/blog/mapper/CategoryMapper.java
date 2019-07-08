package com.ronin.blog.mapper;

import com.ronin.blog.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    int updateCategoryParent(@Param("categoryId") Integer categoryId,@Param("status") Integer status);

    Integer selectCategoryCount();
}