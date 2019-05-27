package com.ronin.blog.controller;

import com.ronin.blog.entity.*;
import com.ronin.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-21 23:43
 */
@Controller
public class MainController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LinksService linksService;

    @Autowired
    private PageService pageService;

    @Autowired
    private UserService userService;


    /**
     * 主页面跳转
     * @param model
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model, HttpSession session){

        //博主置顶
        Article topArticle = articleService.selectTopArticle();
        //最新文章
        List<Article> newArticles = articleService.findNewArticle();
        //博客公告
        Notice notice = noticeService.findNewNotice();
        //所有标签
        List<Tag> tagList = tagService.findAllTag();
        //最热文章
        List<Article> hotArticles = articleService.findHotArticle();
        //友情链接
        List<Links> linksList = linksService.findLinks();

        //存放信息
        model.addAttribute("topArticle",topArticle);
        session.setAttribute("newArticles",newArticles);
        //model.addAttribute("newArticles",newArticles);
        model.addAttribute("notice",notice);
        model.addAttribute("tagList",tagList);
        model.addAttribute("hotArticles",hotArticles);
        session.setAttribute("linksList",linksList);
        //model.addAttribute("linksList",linksList);

        return "reception/index";
    }

    /**
     * 关于我们页面
     * @param model
     * @return
     */
    @RequestMapping(value = "about",method = RequestMethod.GET)
    public String about(Model model){
        //关于我们
        Page page = pageService.selectPageMessage();
        User user = userService.selectUser();

        //存放信息
        model.addAttribute("page",page);
        model.addAttribute("user",user);

        return "reception/about";
    }

}
