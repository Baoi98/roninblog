package com.ronin.blog.service;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.dto.PageInfo;
import com.ronin.blog.entity.Article;
import com.ronin.blog.entity.Tag;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 9:25
 */
public interface TagService {

    /**
     * 查询所有标签
     *
     * @return
     */
    List<Tag> findAllTag();

    /**
     * 查询有特定Tag的文章
     *
     * @return
     */
    List<Article> selectTagArticle(Integer tagId);

    /**
     * 标签页DataTable
     * @param draw
     * @param start
     * @param length
     * @return
     */
    PageInfo<Tag> tagList(Integer draw, Integer start, Integer length);

    /**
     * 根据Tag ID删除
     * @param tagId
     * @return
     */
    BaseResult deleteTagById(Integer tagId);

    /**
     * 新增Tag
     * @param tagName
     * @param tagDescription
     * @return
     */
    BaseResult insertTag(String tagName,String tagDescription);

    /**
     * 更新Tag
     * @param tagId
     * @param tagName
     * @param tagDescription
     * @return
     */
    BaseResult updateTag(Integer tagId,String tagName,String tagDescription);

    /**
     * 根据Id查询Tag内容
     * @param tagId
     * @return
     */
    Tag selectTagById(Integer tagId);

    /**
     * 查询标签数
     * @return
     */
    Integer selectTagCount();
}
