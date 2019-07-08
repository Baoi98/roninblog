package com.ronin.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ronin.blog.entity.Article;
import com.ronin.blog.service.ArticleService;
import com.ronin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-27 21:24
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;


    @RequestMapping(value = "tag_article",method = RequestMethod.GET)
    public String tagArticle(Model model,@RequestParam(value = "tagId",required = true)String tagId,
                             @RequestParam(value = "pageNum",required = true,defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",required = true,defaultValue = "5")Integer pageSize){
        Integer id = Integer.parseInt(tagId);
        //分页
        PageHelper.startPage(pageNum,pageSize);
        //所有文章
        List<Article> articleList = tagService.selectTagArticle(id);
        PageInfo<Article> pageInfoTag = new PageInfo<>(articleList);

        //存放信息
        model.addAttribute("tagId",tagId);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageInfoTag",pageInfoTag);

        return "reception/article";
    }



}
