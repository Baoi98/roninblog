package com.ronin.blog.mapper;

import com.ronin.blog.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer bannerId);

    int insert(Banner record);

    Banner selectByPrimaryKey(Integer bannerId);

    List<Banner> selectAll();

    int updateByPrimaryKey(Banner record);

    List<Banner> selectShowBanner();

    int changeBannerStatus(@Param("bannerId") Integer bannerId, @Param("bannerStatus") Integer bannerStatus);

    Integer selectStartBannerCount();

    Integer selectBannerCount();
}