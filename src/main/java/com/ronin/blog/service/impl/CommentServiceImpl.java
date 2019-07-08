package com.ronin.blog.service.impl;

import com.ronin.blog.common.BeanValidator;
import com.ronin.blog.common.Const;
import com.ronin.blog.common.EmailUtils;
import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Comment;
import com.ronin.blog.mapper.CommentMapper;
import com.ronin.blog.service.CommentService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-7-2 8:25
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Comment> findAllComment() {
        List<Comment> comments = commentMapper.selectAllOrderByDesc();
        return comments;
    }

    @Override
    public BaseResult insertComment(String commentEmail, String commentText) {
        //封装对象
        Comment comment = new Comment();
        comment.setCommentEmail(commentEmail);
        comment.setCommentStatus(0);
        comment.setCommentText(commentText);
        comment.setCommentTime(new Date());
        //邮箱格式验证
        String validator = BeanValidator.validator(comment);
        if(validator != null){
            return BaseResult.fail(validator);
        }
        //插入
        int insert = commentMapper.insert(comment);
        if(insert > 0){
            return BaseResult.success("留言成功");
        }
        else{
            return BaseResult.fail("留言失败");
        }
    }

    @Override
    public Comment CommentDetail(Integer commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        return comment;
    }

    @Override
    public BaseResult sendMail(Integer commentId,String commentEmail, String commentReply) {
        //调用封装的EMAIL类
        int i = EmailUtils.sendEmail(commentEmail, commentReply);
        if(i == 1){
            commentMapper.updateCommentStatus(commentId);
            return BaseResult.success("邮件回复成功");
        }else{
            return BaseResult.fail("邮件回复失败");
        }
    }

    @Override
    public String mailDeleteOne(Integer commentId) {
        int i = commentMapper.deleteByPrimaryKey(commentId);
        if(i == 1){
            return "删除留言成功";
        }
        else {
            return "删除留言失败";
        }
    }

    @Override
    public Map<String, String> mailDeleteArray(String[] commentIdArray) {
        Map<String,String> map = new HashMap<>();
        //String数组转换成Integer
        Integer[] ids = ArticleServiceImpl.strArrayToIntArray(commentIdArray);
        int i = commentMapper.deleteCommentByArray(ids);
        if(i > 0){
            map.put("message","批量删除留言成功");
            return map;
        }else{
            map.put("message","批量删除留言失败");
            return map;
        }
    }

    @Override
    public Integer selectCommentCount() {
        return commentMapper.selectCommentCount();
    }
}
