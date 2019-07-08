package com.ronin.blog.controller;

import com.ronin.blog.common.Const;
import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.User;
import com.ronin.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: 98
 * @Date: 2019-6-3 22:59
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login_html(){
        return "admin/login";
    }

    /**
     * 忘记密码页面
     * @return
     */
    @RequestMapping(value = "forget",method = RequestMethod.GET)
    public String forget_html(){
        return "admin/forget";
    }

    /**
     * 后台主页面
     * @return
     */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main_html(Model model){
        //查询文章总数
        Integer articleCount = articleService.selectArticleCount();
        //查询留言条数
        Integer commentCount = commentService.selectCommentCount();
        //查询轮播图数
        Integer bannerCount = bannerService.selectBannerCount();
        //查询标签数
        Integer tagCount = tagService.selectTagCount();
        //查询分类数
        Integer categoryCount = categoryService.selectCategoryCount();

        model.addAttribute("articleCount",articleCount);
        model.addAttribute("commentCount",commentCount);
        model.addAttribute("bannerCount",bannerCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryCount",categoryCount);

        return "admin/index";
    }

    /**
     * 管理员登陆
     * @param userEmail
     * @param userPass
     * @param session
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "userEmail",required = true)String userEmail,
                        @RequestParam(value = "userPass",required = true)String userPass,
                        HttpSession session, Model model, RedirectAttributes redirectAttributes){
        //登陆方法
        BaseResult baseResult = adminService.login(userEmail, userPass);
        if(baseResult.getData() != null){
            //验证成功，放置用户信息到session中
            User user = (User) baseResult.getData();
            session.setAttribute(Const.USER_SESSION,user);

            //model.addAttribute(Const.BASERESULT,baseResult);
            return "redirect:/admin/main";
        }
        else{
            redirectAttributes.addFlashAttribute(Const.BASERESULT,baseResult);
            return "redirect:/admin/login";
        }
    }

    /**
     * 管理员注销
     * @param session
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }

    /**
     * Ajax管理员密码，忘记密码
     * @param userName
     * @param userEmail
     * @param userPass
     * @param userPassRetype
     * @return
     */
    @RequestMapping(value = "forget",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult forget(@RequestParam(value = "userName",required = true)String userName,
                         @RequestParam(value = "userEmail",required = true)String userEmail,
                         @RequestParam(value = "userPass",required = true)String userPass,
                         @RequestParam(value = "userPassRetype",required = true)String userPassRetype){
        //创建数据传输对象
        BaseResult baseResult = null;

        //判断两次密码是否相同
        if(!userPass.equals(userPassRetype)){
            return BaseResult.fail("两次密码不相同，请重新输入！");
        }
        baseResult = adminService.changePassword(userName, userEmail, userPass);

        return baseResult;
    }

    /**
     * 修改密码页面
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.GET)
    public String changePage(){
        return "admin/change";
    }


    /**
     * Ajax校验管理员Email和Name
     * @param userEmail
     * @param userName
     * @return
     */
    @RequestMapping(value = "check_email_name",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult checkAdminName(@RequestParam(value = "userEmail",required = true)String userEmail,
                                     @RequestParam(value = "userName",required = true)String userName){
        BaseResult baseResult = adminService.checkEmailAndName(userEmail, userName);
        return baseResult;
    }

    /**
     * 管理员修改密码校验
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     * @param retryPassword
     * @return
     */
    @RequestMapping(value = "check_pass",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult checkAdminPass(@RequestParam(value = "userEmail",required = true)String userEmail,
                                     @RequestParam(value = "oldPassword",required = true)String oldPassword,
                                     @RequestParam(value = "newPassword",required = true)String newPassword,
                                     @RequestParam(value = "retryPassword",required = true)String retryPassword){
        BaseResult baseResult = adminService.checkPass(userEmail, oldPassword, newPassword, retryPassword);
        return baseResult;
    }

    @RequestMapping(value = "change_pass",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changeAdminPass(@RequestParam(value = "userName",required = true)String userName,
                                      @RequestParam(value = "userEmail",required = true)String userEmail,
                                      @RequestParam(value = "newPassword",required = true)String newPassword,
                                      HttpSession session) throws InterruptedException {
        Thread.sleep(2000);
        BaseResult baseResult = adminService.changePassword(userName, userEmail, newPassword);
        session.invalidate();
        return baseResult;
    }


}
