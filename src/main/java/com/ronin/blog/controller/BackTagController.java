package com.ronin.blog.controller;

import com.ronin.blog.dto.BaseResult;
import com.ronin.blog.dto.PageInfo;
import com.ronin.blog.entity.Tag;
import com.ronin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-6-16 11:11
 */
@Controller
@RequestMapping(value = "/admin")
public class BackTagController {

    @Autowired
    private TagService tagService;


    /**
     * 所有标签页面
     * @return
     */
    @RequestMapping(value = "tag",method = RequestMethod.GET)
    public String tagPage(){
        return "/admin/tag";
    }


    /**
     * DataTable分页查询
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "tag_list",method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<Tag> tagList(@RequestParam(value = "draw")String draw,
                                 @RequestParam(value = "start")String start,
                                 @RequestParam(value = "length")String length){
        //转换为整形
        Integer drawInt = Integer.parseInt(draw);
        Integer startInt = Integer.parseInt(start);
        Integer lengthInt = Integer.parseInt(length);

        //数据返回对象
        PageInfo<Tag> pageInfo = tagService.tagList(drawInt,startInt,lengthInt);

        return pageInfo;
    }

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    @RequestMapping(value = "tag_delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult tagDelete(@RequestParam(value = "tagId",required = true)String tagId){
        //转换类型
        Integer id = Integer.parseInt(tagId);
        //删除标签
        BaseResult baseResult = tagService.deleteTagById(id);

        return baseResult;
    }

    /**
     * Ajax查询Tag内容
     * @param tagId
     * @return
     */
    @RequestMapping(value = "tag_detail",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> tagDetail(@RequestParam(value = "tagId",required = true)String tagId){
        //转换类型
        Integer id = Integer.parseInt(tagId);
        //查询Tag
        Tag tag = tagService.selectTagById(id);
        //Map
        Map<String,Object> result = new HashMap<>();
        result.put("tId",tag.getTagId());
        result.put("tName",tag.getTagName());
        result.put("tDescription",tag.getTagDescription());

        return result;
    }

    /**
     * Ajax修改或新增标签信息
     * @param tagId
     * @param tagName
     * @param tagDescription
     * @return
     */
    @RequestMapping(value = "tag_change",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult tagAddOrChange(@RequestParam(value = "tagId",required = false)String tagId,
                                     @RequestParam(value = "tagName",required = true)String tagName,
                                     @RequestParam(value = "tagDescription",required = true)String tagDescription){
        //判断是否有ID，没有则为新增
        if(StringUtils.isEmpty(tagId)){
            BaseResult baseResult = tagService.insertTag(tagName, tagDescription);
            return baseResult;
        }
        //转换类型
        Integer id = Integer.parseInt(tagId);
        //更新
        BaseResult baseResult = tagService.updateTag(id, tagName, tagDescription);

        return baseResult;
    }

}
