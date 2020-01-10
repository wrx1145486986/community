package com.wrx.community;

import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        String str = "123456!";

        Pattern p = Pattern.compile("123");

        Matcher matcher = p.matcher("str");

        System.out.println(matcher.toString());

    }

}
