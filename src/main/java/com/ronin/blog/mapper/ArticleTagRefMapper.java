package com.ronin.blog.mapper;

import com.ronin.blog.entity.ArticleTagRef;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ArticleTagRefMapper {
    int deleteByPrimaryKey(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    int insert(ArticleTagRef record);

    List<ArticleTagRef> selectAll();

    List<ArticleTagRef> selectArticleTagRefByArticleId(Integer articleId);

    List<ArticleTagRef> selectArticleTagRefByTagId(Integer tagId);

    int insertByInteger(Map<String,Object> param);

    int updateArticleTagRef(@Param(value = "articleId") Integer articleId,Integer[] tagId);
}