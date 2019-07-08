package com.ronin.blog.mapper;

import com.ronin.blog.entity.Article;
import com.ronin.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {

    int deleteByPrimaryKey(Integer tagId);

    int insert(@Param(value = "tagName") String tagName,@Param(value = "tagDescription") String tagDescription);

    Tag selectByPrimaryKey(Integer tagId);

    List<Tag> selectAll();

    int updateByPrimaryKey(Tag record);

    List<Tag> selectByDataTable(@Param(value = "start") Integer start,@Param(value = "length") Integer length);

    Integer selectTagCount();

}