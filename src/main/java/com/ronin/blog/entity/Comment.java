package com.ronin.blog.entity;

import com.ronin.blog.common.RegexpUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Comment {

    private Integer commentId;

    @Pattern(regexp = RegexpUtils.EMAIL ,message = "邮箱格式不正确")
    private String commentEmail;

    private Integer commentStatus;

    private Date commentTime;

    @Length(min = 1,message = "留言内容不能为空")
    private String commentText;

}