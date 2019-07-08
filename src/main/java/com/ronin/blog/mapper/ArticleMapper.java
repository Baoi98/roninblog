package com.ronin.blog.mapper;

import com.ronin.blog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    Article selectByPrimaryKey(Integer articleId);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);

    /**
     * 倒序查询最热文章
     * @return
     */
    List<Article> findHotArticle();

    /**
     * 查询最新文章
     * @return
     */
    List<Article> findNewArticle();

    /**
     * 博主置顶
     * @return
     */
    Article selectTopArticle();

    /**
     * 根据ID查询上一篇或下一篇文章
     * @param articleId
     * @return
     */
    Article selectLastOrNextArticleById(Integer articleId);

    /**
     * 模糊查询
     * @param keyWord
     * @return
     */
    List<Article> selectArticleByKeyWord(String keyWord);

    /**
     * 查询有特定Tag的文章
     * @return
     */
    List<Article> selectTagArticle(Integer tagId);

    /**
     * 查询文章总数
     * @return
     */
    Integer selectArticleCount();

    /**
     * DataTable分页数据
     * @param params
     * @return
     */
    List<Article> page(@Param(value = "params") Map<String,Object> params);
}