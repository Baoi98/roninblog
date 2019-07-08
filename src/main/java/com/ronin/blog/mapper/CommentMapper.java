package com.ronin.blog.mapper;

import com.ronin.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAllOrderByDesc();

    int updateCommentStatus(Integer commentId);

    int deleteCommentByArray(@Param(value = "ids")Integer[] ids);

    Integer selectCommentCount();
}