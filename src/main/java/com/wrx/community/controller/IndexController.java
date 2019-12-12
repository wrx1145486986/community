package com.wrx.community.controller;


import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

//  设置首页访问路径
    @GetMapping("/")
    public String index(HttpServletRequest req, HttpServletResponse resp) {

        Cookie[] cookies = req.getCookies();
        for(Cookie cookie: cookies){
            // 查询cookie 中是否存在 token字段
            if (cookie.getName() != "" && cookie.getName().equals("token")){
                // 获取token中的信息
                String token = cookie.getValue();
                // 查库
                User user = userMapper.findByToken(token);

                if (user != null){
                    req.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        return "index";
    }

}
