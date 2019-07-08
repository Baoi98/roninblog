package com.ronin.blog.entity;

import lombok.Data;

@Data
public class Category {

    private Integer categoryId;

    private Integer categoryPid;

    private String categoryName;

    private String categoryDescription;

    private Integer categoryParent;

    private String categoryIcon;

}