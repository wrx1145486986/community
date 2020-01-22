package com.wrx.community.service;

import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import com.wrx.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
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
        } else {
            // 查询到 user时做更新token操作
            User dbUser = users.get(0);

            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setBio(user.getBio());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }

    }


}
