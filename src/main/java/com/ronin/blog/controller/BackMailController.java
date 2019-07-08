package com.ronin.blog.controller;

import com.ronin.blog.common.Const;
import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.Comment;
import com.ronin.blog.service.CommentService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-6-28 11:04
 */
@Controller
@RequestMapping(value = "/admin")
public class BackMailController {

    @Autowired
    private CommentService commentService;

    /**
     * 后台留言页面
     * @param model
     * @return
     */
    @RequestMapping(value = "mail",method = RequestMethod.GET)
    public String mailPage(Model model){
        List<Comment> commentList = commentService.findAllComment();
        model.addAttribute("commentList",commentList);
        return "/admin/mail";
    }

    /**
     * 回复邮件
     * @param commentId
     * @param commentReply
     * @param commentEmail
     * @param redirectAttributes
     * @return
     * @throws InterruptedException
     * @throws EmailException
     */
    @RequestMapping(value = "mail_send",method = RequestMethod.POST)
    public String mailSend(@RequestParam(value = "commentId",required = false)String commentId,
                           @RequestParam(value = "commentReply",required = false)String commentReply,
                           @RequestParam(value = "commentEmail",required = false)String commentEmail,
                           RedirectAttributes redirectAttributes) throws InterruptedException, EmailException {
        if(!StringUtils.isEmpty(commentId) && StringUtils.isEmpty(commentReply) && StringUtils.isEmpty(commentEmail)){
            Integer id = Integer.parseInt(commentId);
            //Thread.sleep(1000);
            BaseResult baseResult = commentService.sendMail(id,commentEmail, commentReply);
            redirectAttributes.addFlashAttribute("baseResult","发送成功");
        }
        else{
            redirectAttributes.addFlashAttribute("baseResult","没有选中留言无法回复哦....");
        }
        return "redirect:/admin/mail";
    }

    /**
     * Ajax删除单一留言
     * @param commentId
     * @return
     */
    @RequestMapping(value = "mail_delete_one",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> mailDeleteOne(@RequestParam(value = "commentId",required = true)String commentId){
        Map<String,String> map = new HashMap<>();
        Integer id = Integer.parseInt(commentId);
        String message = commentService.mailDeleteOne(id);
        map.put("message",message);
        return map;
    }

    /**
     * Ajax批量删除留言
     * @param commentIdArray
     * @return
     */
    @RequestMapping(value = "mail_delete_array",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> mailDeleteArray(@RequestParam(value = "commentIdArray",required = true)String[] commentIdArray){
        Map<String, String> map = commentService.mailDeleteArray(commentIdArray);
        return map;
    }

}
