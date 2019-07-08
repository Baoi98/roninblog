package com.ronin.blog.controller;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Category;
import com.ronin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-6-16 11:13
 */
@Controller
@RequestMapping(value = "/admin")
public class BackCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 后台分类页面
     * @param model
     * @return
     */
    @RequestMapping(value = "category",method = RequestMethod.GET)
    public String categoryPage(Model model){
        //查询数据
        List<Category> categoryList = categoryService.findAllCategory();
        //返回信息
        model.addAttribute("categoryList",categoryList);
        return "/admin/category";
    }


    /**
     * 分类详情
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "cate_detail",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> categoryDetail(@RequestParam(value = "categoryId",required = false)String categoryId){
        Map<String, Object> map = new HashMap<>();
        //判断ID是否为空，为空只是为了查询分类集合
        if(!StringUtils.isEmpty(categoryId)) {
            Integer id = Integer.parseInt(categoryId);
            map = categoryService.selectCategotyDetail(id);
        }
        else{
            map = categoryService.selectCategotyDetail(null);
        }
        return map;
    }


    /**
     * 分类新增或修改
     * @param category
     * @return
     */
    @RequestMapping(value = "cate_edit",method = RequestMethod.POST)
    public String categoryEdit(Category category){
        int i = categoryService.categoryEdit(category);
        return "redirect:/admin/category";
    }

    @RequestMapping(value = "cate_delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult categoryDelete(@RequestParam(value = "categoryId",required = true)String categoryId){
        //转换类型
        Integer id = Integer.parseInt(categoryId);
        BaseResult baseResult = categoryService.deleteCategoryById(id);
        return baseResult;
    }


}
