package com.ronin.blog.controller;

import com.ronin.blog.common.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @Author: 98
 * @Date: 2019-6-10 21:01
 */
@Controller
@RequestMapping(value = "/admin")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 文件上传
     * @param dropzFile
     * @param editorFile
     * @param request
     * @return
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> DropZone(MultipartFile dropzFile,
                                       @RequestParam(value = "editormd-image-file", required = false) MultipartFile editorFile,
                                       HttpServletRequest request){
        //数据返回对象
        Map<String,Object> result = new HashMap<>();
        //判断是dropZone文件还是editor文件
        MultipartFile myFile = dropzFile == null ? editorFile : dropzFile;
        if(myFile != null){
            //获取文件名
            String fileName = myFile.getOriginalFilename();
            //获取文件后缀名
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            //获取文件上传文件夹路径
            String filePath = request.getSession().getServletContext().getRealPath(Const.UPLOAD_PATH);
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
                myFile.transferTo(file);
            } catch (IOException e) {
                logger.error("博客展示图上传出错！！！");
            }
            //拼装url返回值
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + Const.UPLOAD_PATH + filePrefix + fileSuffix;
            //根据不同的文件封装返回信息
            if(dropzFile != null){
                result.put("path",url);
            }
            /**
             * success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
             * message : "提示的信息，上传成功或上传失败及错误信息等。",
             * url     : "图片地址"        // 上传成功时才返回
             */
            if(editorFile != null){
                result.put("success",1);
                result.put("message","上传成功");
                result.put("url",url);
            }
        }
        //文件为空
        else{
            //根据不同的文件封装返回信息
            if(dropzFile != null){
                result.put("path","");
            }
            /**
             * success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
             * message : "提示的信息，上传成功或上传失败及错误信息等。",
             * url     : "图片地址"        // 上传成功时才返回
             */
            if(editorFile != null){
                result.put("success",0);
                result.put("message","上传失败");
            }
        }

        return result;
    }


}
