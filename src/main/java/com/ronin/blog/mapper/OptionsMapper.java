package com.ronin.blog.mapper;

import com.ronin.blog.entity.Options;
import java.util.List;

public interface OptionsMapper {
    int deleteByPrimaryKey(Integer optionId);

    int insert(Options record);

    Options selectByPrimaryKey(Integer optionId);

    List<Options> selectAll();

    int updateByPrimaryKey(Options record);
}