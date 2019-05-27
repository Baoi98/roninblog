package com.ronin.blog.entity;

import lombok.Data;

@Data
public class Tag {

    private Integer tagId;

    private String tagName;

    private String tagDescription;

}