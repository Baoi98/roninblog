package com.ronin.blog.service.impl;

import com.ronin.blog.entity.Banner;
import com.ronin.blog.mapper.BannerMapper;
import com.ronin.blog.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 98
 * @Date: 2019-7-4 16:38
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;


    @Override
    public List<Banner> selectBannerList() {
        return bannerMapper.selectAll();
    }

    @Override
    public int insertBanner(String url) {
        Banner banner = new Banner();
        banner.setBannerUrl(url);
        banner.setBannerStatus(0);
        return bannerMapper.insert(banner);
    }

    @Override
    public List<Banner> selectShowBanner() {
        return bannerMapper.selectShowBanner();
    }

    @Override
    public Map<String, Object> changeBannerStatus(Integer bannerId, Integer bannerStatus) {
        Map<String, Object> map = new HashMap<>();
        //查询启用了多少张轮播图
        Integer count = bannerMapper.selectStartBannerCount();
        if(bannerStatus == 1){
            //禁用
            bannerStatus = 0;
            //判断禁用后，启用的轮播图是否少于3张，如果少于3张就禁止禁用
            if(count - 1 < 3){
                map.put("message","至少要三张轮播图，禁止再禁用");
                return map;
            }

        }
        else if(bannerStatus == 0){
            //启用
            bannerStatus = 1;
            //判断启用后，启用的轮播图是否多于5张，如果多于5张就禁止启用
            if(count + 1 > 5){
                map.put("message","最多只能启用五张轮播图，禁止再启用");
                return map;
            }
        }
        //全部通过后修改数据
        int i = bannerMapper.changeBannerStatus(bannerId, bannerStatus);
        if(i == 1){
            map.put("message","修改成功");
            map.put("status",bannerStatus);
            return map;
        }
        else{
            map.put("message","修改失败");
            return map;
        }

    }

    @Override
    public Integer selectBannerCount() {
        return bannerMapper.selectBannerCount();
    }


}
