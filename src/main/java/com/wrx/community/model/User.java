package com.wrx.community.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String avatarUrl;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
}
