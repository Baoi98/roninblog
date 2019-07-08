package com.ronin.blog.service.impl;

import com.ronin.blog.common.BeanValidator;
import com.ronin.blog.common.Const;
import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.entity.User;
import com.ronin.blog.mapper.UserMapper;
import com.ronin.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @Author: 98
 * @Date: 2019-6-3 23:07
 */
@Service
@Transactional
@SuppressWarnings("all")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public BaseResult login(String userEmail, String userPass) {
        User user = new User();
        user.setUserEmail(userEmail);
        user.setUserPass(userPass);
        String validator = BeanValidator.validator(user);
        //验证不通过
        if(validator != null){
            return BaseResult.fail(validator);
        }
        //验证通过
        else{
            //Email查询用户
            user = userMapper.selectByUserEmail(userEmail);
            if(StringUtils.isEmpty(user)){
                return BaseResult.fail(Const.USER_NO_EXIST);
            }
            //用户存在，校验密码
            String MD5Password = DigestUtils.md5DigestAsHex(userPass.getBytes());
            if(!MD5Password.equals(user.getUserPass())){
                return BaseResult.fail(Const.PASSWORD_ERROR);
            }
            //全部验证通过
            return BaseResult.success(Const.LOGIN_SUCCESS,user);
        }

    }

    @Override
    public BaseResult changePassword(String userName, String userEmail, String userPass) {
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPass(userPass);
        String validator = BeanValidator.validator(user);
        //验证不通过
        if(validator != null){
            return BaseResult.fail(validator);
        }
        //通过用户名查询所有信息
        user = userMapper.selectByUserName(userName);
        if(StringUtils.isEmpty(user)){
            return BaseResult.fail(Const.USER_NO_EXIST);
        }
        if(!userEmail.equals(user.getUserEmail())){
            return BaseResult.fail(Const.EMAIL_NO_MATCHING);
        }
        //加密密码
        String MD5Password = DigestUtils.md5DigestAsHex(userPass.getBytes());
        //修改用户密码
        user.setUserPass(MD5Password);
        int i = userMapper.updateByPrimaryKey(user);
        if(i != 1){
            return BaseResult.fail("密码修改失败！");
        }

        return BaseResult.success("修改密码成功");
    }

    @Override
    public BaseResult checkEmailAndName(String userEmail, String userName) {
        //校验数据是否为空
        BaseResult baseResult = checkNull(userEmail, userName, null, null, null);
        if(baseResult != null){
            return baseResult;
        }
        //查询email的管理员是否存在
        User user = userMapper.selectByUserEmail(userEmail);
        if(user == null){
            return BaseResult.fail("Email不存在");
        }
        if(!userName.equals(user.getUserName())){
            return BaseResult.fail("用户名与Email管理员不匹配");
        }

        return BaseResult.success("校验通过");
    }

    @Override
    public BaseResult checkPass(String userEmail,String oldPassword, String newPassword, String retryPassword) {
        //校验数据是否为空
        BaseResult baseResult = checkNull(userEmail, null, oldPassword, newPassword, retryPassword);
        if(baseResult != null){
            return baseResult;
        }
        //查询email的管理员是否存在
        User user = userMapper.selectByUserEmail(userEmail);
        if(user == null){
            return BaseResult.fail("Email不存在");
        }
        //校验MD5密码
        String md5OldPass = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if(!md5OldPass.equals(user.getUserPass())){
            return BaseResult.fail("旧密码错误");
        }
        if(!newPassword.equals(retryPassword)){
            return BaseResult.fail("新密码与重复密码不匹配");
        }

        return BaseResult.success("校验通过");
    }


    private static BaseResult checkNull(String userEmail,String userName,String oldPassword, String newPassword, String retryPassword){
        if("".equals(userEmail)){
            return BaseResult.fail("Email不能为空");
        }
        if("".equals(userName)){
            return BaseResult.fail("管理员名不能为空");
        }
        if("".equals(oldPassword)){
            return BaseResult.fail("旧密码不能为空");
        }
        if("".equals(newPassword)){
            return BaseResult.fail("新密码不能为空");
        }
        if("".equals(retryPassword)){
            return BaseResult.fail("重复新密码不能为空");
        }
        return null;
    }
}
