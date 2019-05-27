package com.ronin.blog.mapper;

import com.ronin.blog.entity.ArticleCategoryRef;
import java.util.List;

public interface ArticleCategoryRefMapper {
    int insert(ArticleCategoryRef record);

    List<ArticleCategoryRef> selectAll();
}