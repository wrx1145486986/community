package com.wrx.community.controller;


import com.wrx.community.dto.QuestionDTO;
import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import com.wrx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

//  设置首页访问路径
    @GetMapping("/")
    public String index(HttpServletRequest req, HttpServletResponse resp,Model model)throws Exception {

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 查询cookie 中是否存在 token字段
                if (cookie.getName() != "" && cookie.getName().equals("token")) {
                    // 获取token中的信息
                    String token = cookie.getValue();
                    // 查库
                    User user = userMapper.findByToken(token);

                    if (user != null) {
                        req.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

            // 将结果查询出来使用 model 将各个参数值传递到前端页面
            List<QuestionDTO> questionDTOList = questionService.queryAllQuestion();
            // 回传前端页面
            model.addAttribute("questionDTOList",questionDTOList);

            return "index";
        }
        return "index";
    }
}
