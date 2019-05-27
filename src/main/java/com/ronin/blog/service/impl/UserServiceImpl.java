package com.ronin.blog.service.impl;

import com.ronin.blog.entity.User;
import com.ronin.blog.mapper.UserMapper;
import com.ronin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-24 16:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUser() {
        List<User> userList = userMapper.selectAll();
        User user = null;
        if(!StringUtils.isEmpty(userList)){
            user = userList.get(0);
        }
        return user;
    }
}
