package com.ronin.blog.service;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-6-14 18:31
 */
public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAllCategory();

    /**
     * 根据ID查询分类详情
     * @param categoryId
     * @return
     */
    Map<String,Object> selectCategotyDetail(Integer categoryId);

    /**
     * 分类新增修改
     * @param category
     * @return
     */
    int categoryEdit(Category category);

    /**
     * 根据ID删除分类
     * @param categoryId
     * @return
     */
    BaseResult deleteCategoryById(Integer categoryId);

    /**
     * 查询分类数
     * @return
     */
    Integer selectCategoryCount();
}
