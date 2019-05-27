package com.ronin.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: 98
 * @Date: 2019-5-26 22:11
 */
@Controller
public class CommentController {


    @RequestMapping(value = "comment",method = RequestMethod.GET)
    public String comment(){

        return "reception/comment";
    }


    @RequestMapping(value = "mood",method = RequestMethod.GET)
    public String mood(){

        return "reception/moodList";
    }



}
