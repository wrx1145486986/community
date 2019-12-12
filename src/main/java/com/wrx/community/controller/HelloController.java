package com.wrx.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

//  设置首页访问路径
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
