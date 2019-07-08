package com.ronin.blog.controller;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Comment;
import com.ronin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-26 22:11
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 留言页面
     * @param model
     * @return
     */
    @RequestMapping(value = "comment",method = RequestMethod.GET)
    public String comment(Model model){
        //所有留言
        List<Comment> commentList = commentService.findAllComment();
        model.addAttribute("commentList",commentList);

        return "reception/comment";
    }

    /**
     * Ajax用户留言
     * @param commentText
     * @param commentEmail
     * @return
     */
    @RequestMapping(value = "comment_submit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult commentSubmit(@RequestParam(value = "commentText")String commentText,
                                    @RequestParam(value = "commentEmail")String commentEmail){
        //留言功能
        BaseResult baseResult = commentService.insertComment(commentEmail,commentText);
        return baseResult;
    }

    /**
     * Ajax留言详情
     * @param commentId
     * @return
     */
    @RequestMapping(value = "comment_detail",method = RequestMethod.POST)
    @ResponseBody
    public Comment commentDetail(@RequestParam(value = "commentId")String commentId){
        //留言详情
        Integer id = Integer.parseInt(commentId);
        Comment comment = commentService.CommentDetail(id);
        return comment;
    }



}
