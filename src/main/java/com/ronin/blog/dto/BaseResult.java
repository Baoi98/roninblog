package com.ronin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 98
 * @Date: 2019-5-24 16:22
 */
@Data
public class BaseResult<T> implements Serializable {

    public static final int STATUS_SECCESS = 200;
    public static final int STATUS_FAIL = 500;

    private int status;
    private String message;

    public static BaseResult success(){
        return BaseResult.createBaseResult(STATUS_SECCESS,"成功");
    }

    public static BaseResult success(String message){
        return BaseResult.createBaseResult(STATUS_SECCESS,message);
    }

    public static BaseResult fail(){
        return BaseResult.createBaseResult(STATUS_FAIL,"失败");
    }

    public static BaseResult fail(String message){
        return BaseResult.createBaseResult(STATUS_FAIL,message);
    }

    public static BaseResult fail(int status,String message){
        return BaseResult.createBaseResult(status,message);
    }


    private static BaseResult createBaseResult(int status,String message){
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        return baseResult;
    }

}
