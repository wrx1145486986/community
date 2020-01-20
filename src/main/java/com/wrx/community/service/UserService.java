package com.wrx.community.service;

import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){

        User dbUser = userMapper.queryByAccountId(user.getAccountId());

        if (dbUser == null){
            // 当未查到user时做插入操作
            User newUser = new User();

            newUser.setToken(user.getToken());
            newUser.setName(user.getName());
            newUser.setAccountId(user.getId().toString());
            newUser.setGmtCreate(System.currentTimeMillis());
            newUser.setGmtModified(newUser.getGmtCreate());
            newUser.setBio(user.getBio());
            newUser.setAvatarUrl(user.getAvatarUrl());

            // 将用户数据插入数据库中
            userMapper.insert(newUser);
        }else{
            // 查询到 user时做更新token操作

        dbUser.setToken(user.getToken());
        dbUser.setName(user.getName());
        dbUser.setGmtModified(System.currentTimeMillis());
        dbUser.setBio(user.getBio());
        dbUser.setAvatarUrl(user.getAvatarUrl());

        userMapper.update(dbUser);
    }

    }


}
