package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Tag;
import com.ronin.blog.mapper.TagMapper;
import com.ronin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 98
 * @Date: 2019-5-22 9:26
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findAllTag() {
        List<Tag> tagList = tagMapper.selectAll();
        return tagList;
    }
}
