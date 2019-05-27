package com.ronin.blog.service;

import com.ronin.blog.entity.Article;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 14:33
 */
public interface ArticleService {

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
     * 所有文章
     * @return
     */
    List<Article> selectAllArticle(Integer pageSize);

    /**
     * 根据ID查询文章
     * @param articleId
     * @return
     */
    Article selectArticleById(Integer articleId);

    /**
     * 根据ID查询上一篇或下一篇文章
     * @param articleId
     * @return
     */
    Article selectLastOrNextArticleById(Integer articleId);

    /**
     * 更新文章
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 模糊查询
     * @param keyWord
     * @return
     */
    List<Article> selectArticleByKeyWord(String keyWord);

}
