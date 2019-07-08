package com.ronin.blog.service.impl;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.dto.PageInfo;
import com.ronin.blog.entity.*;
import com.ronin.blog.mapper.*;
import com.ronin.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;

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
    public List<Article> selectAllArticle() {

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

    @Override
    public Integer selectArticleCount() {
        return articleMapper.selectArticleCount();
    }

    @Override
    public PageInfo<Article> page(Integer draw,Integer start, Integer length,Article article) {
        //数据返回对象
        PageInfo<Article> pageInfo = new PageInfo<>();
        //封装查询数据
        Map<String,Object> params = new HashMap<>();
        params.put("start",start);
        params.put("length",length);
        //判断文章对象是否为空
        if(!StringUtils.isEmpty(article)){
            String title = article.getArticleTitle();
            String content = article.getArticleContent();
            Integer status = article.getArticleStatus();
            params.put("title",title);
            params.put("content",content);
            params.put("status",status);
        }
        //查询数据
        List<Article> articleList = articleMapper.page(params);
        //数据条数
        Integer count = articleMapper.selectArticleCount();

        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(articleList);
        pageInfo.setError("");

        return pageInfo;
    }

    @Override
    public int insertArticle(Article article) {
        return articleMapper.insert(article);
    }

    @Transactional
    @Override
    public BaseResult deleteArticle(Integer articleId) {
        //操作数据库
        int i = articleMapper.deleteByPrimaryKey(articleId);
        //判断是否删除成功
        if(i>0){
            return BaseResult.success("删除成功");
        }
        else{
            return BaseResult.fail("删除失败");
        }
    }

    @Transactional
    @Override
    public Article insertArticleCategoryAndTag(Integer categoryId, Article article, String[] tag) {
        //转换数组类型
        Integer[] tagId = strArrayToIntArray(tag);
        //文章与分类关系对象
        ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef();
        articleCategoryRef.setArticleId(article.getArticleId());
        articleCategoryRef.setCategoryId(categoryId);
        //新增文章标签关系
        Map<String,Object> param = new HashMap<>();
        param.put("articleId",article.getArticleId());
        param.put("tagId",tagId);
        //新增文章分类关系
        articleCategoryRefMapper.insert(articleCategoryRef);
        //新增文章标签关系
        articleTagRefMapper.insertByInteger(param);

        return article;
    }

    /**
     * String数组转换为Integer数组
     * @param a
     * @return
     */
    public static Integer[] strArrayToIntArray(String[] a){
        Integer[] b = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }

}
