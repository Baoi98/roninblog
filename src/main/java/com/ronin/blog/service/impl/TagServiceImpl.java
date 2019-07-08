package com.ronin.blog.service.impl;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.dto.PageInfo;
import com.ronin.blog.entity.Article;
import com.ronin.blog.entity.ArticleTagRef;
import com.ronin.blog.entity.Tag;
import com.ronin.blog.entity.User;
import com.ronin.blog.mapper.ArticleMapper;
import com.ronin.blog.mapper.ArticleTagRefMapper;
import com.ronin.blog.mapper.TagMapper;
import com.ronin.blog.mapper.UserMapper;
import com.ronin.blog.service.ArticleService;
import com.ronin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 9:26
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public List<Tag> findAllTag() {
        List<Tag> tagList = tagMapper.selectAll();
        return tagList;
    }

    @Transactional
    @Override
    public List<Article> selectTagArticle(Integer tagId) {
        List<Article> articleList = articleMapper.selectTagArticle(tagId);
        return articleList;
    }

    @Override
    public PageInfo<Tag> tagList(Integer draw, Integer start, Integer length) {
        PageInfo<Tag> pageInfo = new PageInfo<>();
        //查询数据
        List<Tag> tagList = tagMapper.selectByDataTable(start, length);
        Integer count = tagMapper.selectTagCount();
        //封装数据返回对象
        pageInfo.setDraw(draw);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setRecordsTotal(count);
        pageInfo.setData(tagList);
        pageInfo.setError("");

        return pageInfo;
    }

    @Override
    public BaseResult deleteTagById(Integer tagId) {
        //删除标签
        int i = tagMapper.deleteByPrimaryKey(tagId);
        if(i>0){
            return BaseResult.success("删除成功");
        }
        else{
            return BaseResult.fail("删除失败");
        }
    }

    @Override
    public BaseResult insertTag(String tagName, String tagDescription) {
        //新增
        int insert = tagMapper.insert(tagName, tagDescription);
        if(insert>0){
            return BaseResult.success("更新标签成功");
        }
        else{
            return BaseResult.fail("更新标签失败");
        }
    }

    @Override
    public BaseResult updateTag(Integer tagId, String tagName, String tagDescription) {
        //封装对象
        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setTagName(tagName);
        tag.setTagDescription(tagDescription);
        //更新
        int i = tagMapper.updateByPrimaryKey(tag);
        if(i>0){
            return BaseResult.success("更新标签成功");
        }
        else{
            return BaseResult.fail("更新标签失败");
        }
    }

    @Override
    public Tag selectTagById(Integer tagId) {
        return tagMapper.selectByPrimaryKey(tagId);
    }

    @Override
    public Integer selectTagCount() {
        return tagMapper.selectTagCount();
    }


}
