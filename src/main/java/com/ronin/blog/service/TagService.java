package com.ronin.blog.service;

import com.ronin.blog.entity.Tag;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 9:25
 */
public interface TagService {

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> findAllTag();


}
