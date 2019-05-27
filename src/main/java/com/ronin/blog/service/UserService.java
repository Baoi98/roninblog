package com.ronin.blog.service;

import com.ronin.blog.entity.User;

/**
 * @Author: 98
 * @Date: 2019-5-24 16:41
 */
public interface UserService {

    /**
     * 查找博主信息
     * @return
     */
    User selectUser();

}
