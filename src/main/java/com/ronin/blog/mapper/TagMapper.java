package com.ronin.blog.mapper;

import com.ronin.blog.entity.Tag;
import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(Tag record);

    Tag selectByPrimaryKey(Integer tagId);

    List<Tag> selectAll();

    int updateByPrimaryKey(Tag record);

    List<Tag> selectTagByArticleId(Integer articleId);

}