package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Notice;
import com.ronin.blog.mapper.NoticeMapper;
import com.ronin.blog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 98
 * @Date: 2019-5-22 14:47
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Notice findNewNotice() {
        return noticeMapper.findNewNotice();
    }
}
