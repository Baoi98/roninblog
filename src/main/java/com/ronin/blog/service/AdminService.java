package com.ronin.blog.service;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.User;

/**
 * @Author: 98
 * @Date: 2019-6-3 23:07
 */
public interface AdminService {

    /**
     * 管理员登陆
     * @param userEmail
     * @param userPass 明文密码
     * @return
     */
    BaseResult login(String userEmail, String userPass);

    /**
     * 修改密码
     * @param userName
     * @param userEmail
     * @param userPass
     * @return
     */
    BaseResult changePassword(String userName,String userEmail,String userPass);

    /**
     * 校验管理员Email和名字
     * @param userEmail
     * @param userName
     * @return
     */
    BaseResult checkEmailAndName(String userEmail,String userName);

    /**
     * 校验管理员修改密码
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     * @param retryPassword
     * @return
     */
    BaseResult checkPass(String userEmail,String oldPassword,String newPassword,String retryPassword);

}
