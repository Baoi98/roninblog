package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Page;
import com.ronin.blog.mapper.PageMapper;
import com.ronin.blog.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-24 16:15
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public Page selectPageMessage() {
        List<Page> pageList = pageMapper.selectAll();
        Page page = null;
        if(!StringUtils.isEmpty(pageList)){
            page = pageList.get(0);
        }
        return page;
    }
}
