package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Links;
import com.ronin.blog.mapper.LinksMapper;
import com.ronin.blog.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-23 9:58
 */
@Service
public class LinksServiceImpl implements LinksService {

    @Autowired
    private LinksMapper linksMapper;

    @Override
    public List<Links> findLinks() {
        return linksMapper.selectAll();
    }
}
