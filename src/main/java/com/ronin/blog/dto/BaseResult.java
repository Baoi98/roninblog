package com.ronin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 数据传输对象
 * @Author: 98
 * @Date: 2019-5-24 16:22
 */
@Data
public class BaseResult<T> implements Serializable {

    public static final int STATUS_SECCESS = 200;
    public static final int STATUS_FAIL = 500;

    private int status;
    private String message;
    private T data;

    public static BaseResult success(){
        return BaseResult.createBaseResult(STATUS_SECCESS,"成功",null);
    }

    public static BaseResult success(String message){
        return BaseResult.createBaseResult(STATUS_SECCESS,message,null);
    }

    public static BaseResult success(String message,Object data){
        return BaseResult.createBaseResult(STATUS_SECCESS,message,data);
    }


    public static BaseResult fail(){
        return BaseResult.createBaseResult(STATUS_FAIL,"失败",null);
    }

    public static BaseResult fail(String message){
        return BaseResult.createBaseResult(STATUS_FAIL,message,null);
    }

    public static BaseResult fail(int status,String message){
        return BaseResult.createBaseResult(status,message,null);
    }

    public static BaseResult fail(int status,String message,Object data){
        return BaseResult.createBaseResult(status,message,data);
    }


    private static BaseResult createBaseResult(int status,String message,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        if(!StringUtils.isEmpty(data)){
            baseResult.setData(data);
        }
        return baseResult;
    }

}
