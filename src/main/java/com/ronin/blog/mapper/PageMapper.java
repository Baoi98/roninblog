package com.ronin.blog.mapper;

import com.ronin.blog.entity.Page;

import java.util.List;

public interface PageMapper {
    int deleteByPrimaryKey(Integer pageId);

    int insert(Page record);

    Page selectByPrimaryKey(Integer pageId);

    List<Page> selectAll();

    int updateByPrimaryKey(Page record);
}