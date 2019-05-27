package com.ronin.blog.service;

import com.ronin.blog.entity.Notice;

/**
 * @Author: 98
 * @Date: 2019-5-22 14:47
 */
public interface NoticeService {

    /**
     * 查询最新的通告
     * @return
     */
    Notice findNewNotice();

}
