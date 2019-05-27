package com.ronin.blog.entity;

import lombok.Data;

@Data
public class ArticleTagRef {

    private Integer articleId;

    private Integer tagId;

}