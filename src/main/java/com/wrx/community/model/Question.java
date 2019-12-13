package com.wrx.community.model;

import lombok.Data;

@Data
public class Question {

    private Integer id;
    private String tittle;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creater;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
