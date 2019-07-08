package com.ronin.blog.service.impl;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Category;
import com.ronin.blog.mapper.CategoryMapper;
import com.ronin.blog.service.CategoryService;
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
 * @Date: 2019-6-14 18:32
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAllCategory() {
        //获取数据
        List<Category> list = categoryMapper.selectAll();
        //目标集合
        List<Category> targetList = new ArrayList<>();
        targetList = sortCategoryList(list,targetList,0);
        return targetList;
    }

    @Override
    public Map<String,Object> selectCategotyDetail(Integer categoryId) {
        //返回数据类型
        Map<String,Object> map = new HashMap<>();
        if(categoryId != null) {
            //查询数据库
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            //封装信息
            if (!StringUtils.isEmpty(category)) {
                map.put("categoryDetail", category);
            }
        }
        //分类集合
        List<Category> list = categoryMapper.selectAll();
        if(list.size() != 0){
            map.put("categoryList",list);
        }
        map.put("message","分类详情");
        return map;
    }

    @Override
    public int categoryEdit(Category category) {
        int i = 0;
        category.setCategoryIcon("");
        //判断是新增还是修改
        if(category.getCategoryId() != null){
            //修改
            categoryMapper.updateCategoryParent(category.getCategoryPid(),1);
            category.setCategoryParent(judgeCategoryParent(category.getCategoryId()));
            i = categoryMapper.updateByPrimaryKey(category);
        }
        else{
            //新增,判断是否是父级节点
            if(category.getCategoryPid() == 0){
                category.setCategoryParent(1);
            }
            else {
                category.setCategoryParent(0);
            }
            i = categoryMapper.insert(category);
        }
        return i;
    }

    @Override
    public BaseResult deleteCategoryById(Integer categoryId) {
        int i = categoryMapper.deleteByPrimaryKey(categoryId);
        //判断是否成功，封装返回数据
        if(i > 0){
            return BaseResult.success("删除分类成功");
        }
        else{
            return BaseResult.fail("删除分类失败");
        }
    }

    @Override
    public Integer selectCategoryCount() {
        return categoryMapper.selectCategoryCount();
    }

    /**
     * 排序
     * @param list
     * @param targetList
     * @param parentId
     * @return
     */
    private List<Category> sortCategoryList(List<Category> list,List<Category> targetList,Integer parentId){
        //遍历集合通过父子级别排序
        for (Category category : list){
            //判断是否是首节点
            if(category.getCategoryPid().equals(parentId)){
                targetList.add(category);
                //判断是否有子节点，有则追加
                if(category.getCategoryParent() == 1){
                    for (Category ccategory : list){
                        if(ccategory.getCategoryPid().equals(category.getCategoryId())){
                            sortCategoryList(list,targetList,category.getCategoryId());
                            break;
                        }
                    }
                }
            }
        }
        return targetList;
    }

    /**
     * 循环判断是否为父节点
     * @param categoryId
     * @return
     */
    private int judgeCategoryParent(Integer categoryId){

        List<Category> categoryList = categoryMapper.selectAll();
        //遍历查找是否有节点的父节点是本身
        for (Category category : categoryList){
            if(categoryId.equals(category.getCategoryPid())){
                return 1;
            }
        }
        return 0;
    }


}
