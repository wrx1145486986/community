package com.wrx.community.dto;


import com.wrx.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {

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
    private User user;

}
