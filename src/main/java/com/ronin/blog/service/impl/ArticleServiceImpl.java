package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Article;
import com.ronin.blog.entity.ArticleTagRef;
import com.ronin.blog.entity.Tag;
import com.ronin.blog.entity.User;
import com.ronin.blog.mapper.ArticleMapper;
import com.ronin.blog.mapper.ArticleTagRefMapper;
import com.ronin.blog.mapper.TagMapper;
import com.ronin.blog.mapper.UserMapper;
import com.ronin.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 14:33
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;

    @Override
    public List<Article> findHotArticle() {
        return articleMapper.findHotArticle();
    }

    @Override
    public List<Article> findNewArticle() {
        return articleMapper.findNewArticle();
    }

    @Override
    public Article selectTopArticle() {
        return articleMapper.selectTopArticle();
    }

    @Transactional
    @Override
    public List<Article> selectAllArticle(Integer pageSize) {

        List<Article> articleList = articleMapper.selectAll();

        for(Article article:articleList) {
            //封装User
            Integer articleUserId = article.getArticleUserId();
            User user = userMapper.selectByPrimaryKey(articleUserId);
            article.setUser(user);
            //封装TagList
            Integer articleId = article.getArticleId();
            List<Tag> tagList = new ArrayList<>();
            List<ArticleTagRef> articleTagRefList = articleTagRefMapper.selectArticleTagRefByArticleId(articleId);
            for (ArticleTagRef articleTagRef:articleTagRefList) {
                Integer tagId = articleTagRef.getTagId();
                Tag tag = tagMapper.selectByPrimaryKey(tagId);
                tagList.add(tag);
            }
            article.setTagList(tagList);
        }

        return articleList;
    }

    @Override
    public Article selectArticleById(Integer articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        return article;
    }

    @Override
    public Article selectLastOrNextArticleById(Integer articleId) {
        Article article = articleMapper.selectLastOrNextArticleById(articleId);
        return article;
    }

    @Transactional
    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateByPrimaryKey(article);
    }

    @Override
    public List<Article> selectArticleByKeyWord(String keyWord) {
        List<Article> articleList = articleMapper.selectArticleByKeyWord(keyWord);

        for(Article article:articleList) {
            //封装User
            Integer articleUserId = article.getArticleUserId();
            User user = userMapper.selectByPrimaryKey(articleUserId);
            article.setUser(user);
            //封装TagList
            Integer articleId = article.getArticleId();
            List<Tag> tagList = new ArrayList<>();
            List<ArticleTagRef> articleTagRefList = articleTagRefMapper.selectArticleTagRefByArticleId(articleId);
            for (ArticleTagRef articleTagRef:articleTagRefList) {
                Integer tagId = articleTagRef.getTagId();
                Tag tag = tagMapper.selectByPrimaryKey(tagId);
                tagList.add(tag);
            }
            article.setTagList(tagList);
        }

        return articleList;
    }

}
