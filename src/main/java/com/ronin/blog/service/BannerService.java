package com.ronin.blog.service;

import com.ronin.blog.entity.Banner;

import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-7-4 16:38
 */
public interface BannerService {

    /**
     * 轮播图集合
     * @return
     */
    List<Banner> selectBannerList();

    /**
     * 新增轮播图
     * @param url
     * @return
     */
    int insertBanner(String url);

    /**
     * 查询前台的轮播图
     * @return
     */
    List<Banner> selectShowBanner();

    /**
     * 修改轮播图状态
     * @param bannerId
     * @param bannerStatus
     * @return
     */
    Map<String,Object> changeBannerStatus(Integer bannerId,Integer bannerStatus);

    /**
     * 查询轮播图总数
     * @return
     */
    Integer selectBannerCount();
}
