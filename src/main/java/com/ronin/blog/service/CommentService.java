package com.ronin.blog.service;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Comment;
import org.apache.commons.mail.EmailException;

import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-7-2 8:25
 */
public interface CommentService {

    /**
     * 前台查询留言
     * @return
     */
    List<Comment> findAllComment();

    /**
     * 用户留言
     * @param commentEmail
     * @param commentText
     * @return
     */
    BaseResult insertComment(String commentEmail,String commentText);

    /**
     * 根据ID查询留言详情
     * @param commentId
     * @return
     */
    Comment CommentDetail(Integer commentId);

    /**
     * 邮件回复
     * @param commentId
     * @param commentEmail
     * @param commentReply
     * @return
     */
    BaseResult sendMail(Integer commentId,String commentEmail,String commentReply) throws EmailException;

    /**
     * 删除单一留言
     * @param commentId
     * @return
     */
    String mailDeleteOne(Integer commentId);

    /**
     * 批量删除留言
     * @param commentIdArray
     * @return
     */
    Map<String,String> mailDeleteArray(String[] commentIdArray);

    /**
     * 查询留言总条数
     * @return
     */
    Integer selectCommentCount();
}
