package com.ronin.blog.entity;

import com.ronin.blog.common.RegexpUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class User {

    private Integer userId;

    @Length(min = 5,max = 20,message = "用户名为 5 - 20 位")
    private String userName;

    @Length(min = 6,max = 30,message = "密码长度需要在6 - 30位之间")
    private String userPass;

    private String userNickname;

    @Pattern(regexp = RegexpUtils.EMAIL ,message = "邮箱格式不正确")
    private String userEmail;

    private String userUrl;

    private String userAvatar;

    private String userLastLoginIp;

    private Date userRegisterTime;

    private Date userLastLoginTime;

    private Integer userStatus;


}