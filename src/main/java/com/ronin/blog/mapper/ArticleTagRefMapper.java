package com.ronin.blog.mapper;

import com.ronin.blog.entity.ArticleTagRef;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagRefMapper {
    int deleteByPrimaryKey(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    int insert(ArticleTagRef record);

    List<ArticleTagRef> selectAll();

    List<ArticleTagRef> selectArticleTagRefByArticleId(Integer articleId);
}