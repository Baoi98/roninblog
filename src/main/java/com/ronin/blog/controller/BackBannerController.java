package com.ronin.blog.controller;

import com.ronin.blog.common.Const;
import com.ronin.blog.entity.Banner;
import com.ronin.blog.service.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: 98
 * @Date: 2019-7-4 16:39
 */
@Controller
@RequestMapping(value = "/admin")
public class BackBannerController {

    private static final Logger logger = LoggerFactory.getLogger(BackBannerController.class);

    @Autowired
    private BannerService bannerService;

    /**
     * 轮播图管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "banner",method = RequestMethod.GET)
    public String bannerPage(Model model){
        List<Banner> bannerList = bannerService.selectBannerList();
        model.addAttribute("bannerList",bannerList);
        return "admin/banner";
    }


    /**
     * 轮播图上传
     * @param dropzFile
     * @param request
     * @return
     */
    @RequestMapping(value = "banner_upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> bannerUpload(MultipartFile dropzFile,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();

        if(dropzFile != null){
            //获取文件名
            String fileName = dropzFile.getOriginalFilename();
            //获取文件后缀名
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            //获取文件上传文件夹路径
            String filePath = request.getSession().getServletContext().getRealPath(Const.BANNER_PATH);
            //判断文件夹是否存在,不存在就创建文件夹
            File file = new File(filePath);
            if(! file.exists()){
                file.mkdirs();
            }
            //UUID替换文件名
            String filePrefix = UUID.randomUUID().toString();
            file = new File(filePath,filePrefix+fileSuffix);
            //上传文件
            try {
                dropzFile.transferTo(file);
            } catch (IOException e) {
                logger.error("轮播图上传出错！！！");
            }
            //拼装url返回值
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + Const.BANNER_PATH + filePrefix + fileSuffix;
            //根据不同的文件封装返回信息
            if(dropzFile != null){
                result.put("path",url);
            }
            //将轮播信息放入数据库
            bannerService.insertBanner(url);
        }
        return result;
    }

    /**
     * 修改轮播图状态
     * @param bannerId
     * @param bannerStatus
     * @return
     */
    @RequestMapping(value = "banner_status",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> bannerStatus(@RequestParam(value = "bannerId",required = true)String bannerId,
                                           @RequestParam(value = "bannerStatus",required = true)String bannerStatus){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(bannerId) && StringUtils.isNotBlank(bannerStatus)){
            Integer id = Integer.parseInt(bannerId);
            Integer status = Integer.parseInt(bannerStatus);
            map = bannerService.changeBannerStatus(id, status);
        }else {
            map.put("message","参数错误");
        }
        return map;
    }



}
