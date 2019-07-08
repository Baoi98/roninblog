package com.ronin.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ronin.blog.entity.Article;
import com.ronin.blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-24 22:54
 */
@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    /**
     * 所有文章
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "article",method = RequestMethod.GET)
    public String article(Model model, @RequestParam(value = "pageNum",required = true,defaultValue = "1")Integer pageNum,
                          @RequestParam(value = "pageSize",required = true,defaultValue = "5")Integer pageSize){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        //所有文章
        List<Article> articleList = articleService.selectAllArticle();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        //最新发布
        //List<Article> newArticle = articleService.findNewArticle();
        //友情链接
        //List<Links> linksList = linksService.findLinks();

        //存放信息
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageInfo",pageInfo);
        //model.addAttribute("newArticle",newArticle);

        return "reception/article";
    }

    /**
     * 文章详情
     * @param model
     * @param articleId
     * @return
     */
    @RequestMapping(value = "article_detail",method = RequestMethod.GET)
    public String articleDetail(Model model,@RequestParam(value = "articleId")String articleId){
        //转换类型
        int id = Integer.parseInt(articleId);
        //查询文章
        Article article = articleService.selectArticleById(id);
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        //更新记录
        int i = articleService.updateArticle(article);
        if(i == 0){
            logger.error("更新文章访问量失败！");
        }
        //查询上一篇
        Article lastArticle = articleService.selectLastOrNextArticleById(id - 1);
        //查询下一篇
        Article nextArticle = articleService.selectLastOrNextArticleById(id + 1);
        //存放信息
        model.addAttribute("article",article);
        model.addAttribute("lastArticle",lastArticle);
        model.addAttribute("nextArticle",nextArticle);

        return "reception/article_detail";
    }

    /**
     * 模糊查询文章
     * @param model
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "article_search",method = RequestMethod.GET)
    public String articleSearch(Model model,@RequestParam(value = "keyWord")String keyWord,
                                @RequestParam(value = "pageNum",required = true,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "pageSize",required = true,defaultValue = "5")Integer pageSize){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articleList = articleService.selectArticleByKeyWord(keyWord);
        PageInfo pageInfoLike = new PageInfo(articleList);
        //保存信息
        model.addAttribute("keyWord",keyWord);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageInfoLike",pageInfoLike);

        return "reception/article";
    }



}
