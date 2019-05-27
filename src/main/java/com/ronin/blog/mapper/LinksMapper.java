package com.ronin.blog.mapper;

import com.ronin.blog.entity.Links;
import java.util.List;

public interface LinksMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(Links record);

    Links selectByPrimaryKey(Integer linkId);

    List<Links> selectAll();

    int updateByPrimaryKey(Links record);
}