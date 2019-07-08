package com.ronin.blog.mapper;

import com.ronin.blog.entity.ArticleCategoryRef;
import com.ronin.blog.entity.User;

import java.util.List;

public interface ArticleCategoryRefMapper {

    int insert(ArticleCategoryRef record);

    List<ArticleCategoryRef> selectAll();

    int updateByPrimaryKey(ArticleCategoryRef articleCategoryRef);

}