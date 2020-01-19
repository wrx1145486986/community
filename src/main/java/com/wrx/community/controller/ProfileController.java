package com.wrx.community.controller;

import com.wrx.community.dto.PageinationDTO;
import com.wrx.community.model.User;
import com.wrx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, HttpServletRequest req,
                          Model model,
                          @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

//        获取拦截器存储在Session中的user数据
        User user = (User) req.getSession().getAttribute("user");

//        如果用户没有登录 则会跳转至登录界面
        if (user == null) {
            return "redirect:/";
        }

//        判定传递进来的 动态地址是什么
        if ("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        } else {
            if ("replies".equals(action)) {
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "最新回复");
            }
        }

        PageinationDTO pageinationDTO = questionService.list(user.getId(), pageNum, size);

        model.addAttribute("pageinationDTO", pageinationDTO);


        return "profile";
    }

}
