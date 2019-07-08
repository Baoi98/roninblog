package com.ronin.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Member {
    private Integer memberId;

    private String memberName;

    private String menberPassword;

    private String memberEmail;

    private Date memberCreate;

}