package com.ronin.blog.service;

import com.ronin.blog.entity.Links;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-23 9:58
 */
public interface LinksService {

    /**
     * 查找
     * @return
     */
    List<Links> findLinks();

}
