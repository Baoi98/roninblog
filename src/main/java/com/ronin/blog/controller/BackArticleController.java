package com.ronin.blog.controller;


import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.dto.PageInfo;
import com.ronin.blog.entity.Article;
import com.ronin.blog.entity.Category;
import com.ronin.blog.entity.Tag;
import com.ronin.blog.service.ArticleService;
import com.ronin.blog.service.CategoryService;
import com.ronin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-6-5 20:54
 */
@Controller
@RequestMapping(value = "/admin")
public class BackArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 后台文章列表
     * @return
     */
    @RequestMapping(value = "article",method = RequestMethod.GET)
    public String article(){
        return "admin/article";
    }

    /**
     * DataTable分页
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<Article> article_table(@RequestParam(value = "draw",required = true,defaultValue = "0")String draw,
                                           @RequestParam(value = "start",required = true,defaultValue = "0")String start,
                                           @RequestParam(value = "length",required = true,defaultValue = "10")String length,
                                           @RequestParam(value = "articleTitle",required = false)String articleTitle,
                                           @RequestParam(value = "articleContent",required = false)String articleContent,
                                           @RequestParam(value = "articleStatus",required = false)String articleStatus){
        Article article = new Article();
        //转换为整形
        Integer drawInt = Integer.parseInt(draw);
        Integer startInt = Integer.parseInt(start);
        Integer lengthInt = Integer.parseInt(length);
        if(articleTitle!=null && articleContent!=null && articleStatus!=null){
            Integer status = Integer.parseInt(articleStatus);
            //封装数据
            article.setArticleTitle(articleTitle);
            article.setArticleStatus(status);
            article.setArticleContent(articleContent);
        }
        //PageInfo为自定义dto对象
        PageInfo<Article> pageInfo = articleService.page(drawInt, startInt, lengthInt,article);

        return pageInfo;
    }

    /**
     * 文章详情
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public String article_detail(@RequestParam(value = "articleId",required = true)String articleId,
                                 Model model){
        //转换类型
        Integer id = Integer.parseInt(articleId);
        //根据ID查询文章内容
        Article article = articleService.selectArticleById(id);
        //返回对象
        model.addAttribute("article",article);

        return "admin/article_detail";
    }

    /**
     * 修改文章页面跳转
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public String article_edit(@RequestParam(value = "articleId",required = true)String articleId,
                               Model model){
        //转换类型
        Integer id = Integer.parseInt(articleId);
        //根据ID查询文章内容
        Article article = articleService.selectArticleById(id);
        //查询所有标签
        List<Tag> tagList = tagService.findAllTag();
        //查询所有分类
        List<Category> categoryList = categoryService.findAllCategory();
        //返回数据
        model.addAttribute("tagList",tagList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("article",article);

        return "admin/article_edit";
    }

    /**
     * 新增文章页面跳转
     * @param model
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String article_add(Model model){
        //查询所有标签
        List<Tag> tagList = tagService.findAllTag();
        //查询所有分类
        List<Category> categoryList = categoryService.findAllCategory();
        //返回数据
        model.addAttribute("tagList",tagList);
        model.addAttribute("categoryList",categoryList);

        return "admin/article_add";
    }


    /**
     * 文章编辑
     * @param article
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "edit_do",method = RequestMethod.POST)
    public String article_edit_do(Article article, RedirectAttributes redirectAttributes,Model model,
                                  @RequestParam(value = "categoryId",required = false)String categoryId,
                                  @RequestParam(value = "tag",required = false)String[] tagId){
        //封装数据
        article.setArticleUserId(1);
        article.setArticleViewCount(0);
        article.setArticleCommentCount(0);
        article.setArticleLikeCount(0);
        article.setArticleUpdateTime(new Date());
        if(article.getArticleCreateTime()==null){
            article.setArticleCreateTime(new Date());
        }
        article.setArticleIsComment(0);
        //更新或新增数据
        int i = 0;
        if(article.getArticleId()==null){
            i = articleService.insertArticle(article);
            articleService.insertArticleCategoryAndTag(Integer.parseInt(categoryId),article,tagId);
        }
        else{
            i = articleService.updateArticle(article);
        }
        //操作成功
        if(i>0){
            redirectAttributes.addFlashAttribute("message","操作成功");
            return "redirect:/admin/article";
        }
        else{
            redirectAttributes.addFlashAttribute("message","操作失败");
            return "redirect:/admin/edit";
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult article_delete(@RequestParam(value = "articleId",required = true)String articleId){
        //转换类型
        Integer id = Integer.parseInt(articleId);
        //删除文章
        BaseResult baseResult = articleService.deleteArticle(id);

        return baseResult;
    }

}
