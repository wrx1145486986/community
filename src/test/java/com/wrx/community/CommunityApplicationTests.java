package com.wrx.community;

import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = userMapper.findByToken("e531b6b0-9ee8-4feb-bb72-e1b7e465a236");

        System.out.println(user);
    }

}
