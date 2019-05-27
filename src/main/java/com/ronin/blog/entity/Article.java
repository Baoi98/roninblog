package com.ronin.blog.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {

    private Integer articleId;

    private Integer articleUserId;

    private String articleTitle;

    private String articleSummary;

    private Integer articleViewCount;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    private Integer articleIsComment;

    private Integer articleStatus;

    private Date articleUpdateTime;

    private Date articleCreateTime;

    private String articleHtml;

    private String articleContent;

    private String articleUrl;

    private User user;

    private List<Tag> tagList;
}