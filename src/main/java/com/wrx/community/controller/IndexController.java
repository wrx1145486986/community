package com.wrx.community.controller;


import com.wrx.community.dto.PageinationDTO;
import com.wrx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

//  设置首页访问路径
    @GetMapping("/")
    public String index(Model model, @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "size" , defaultValue = "5") Integer size
    )throws Exception {

            // 将结果查询出来使用 model 将各个参数值传递到前端页面
            PageinationDTO pageinationDTO= questionService.queryQuestion(pageNum,size);
            // 回传前端页面
            model.addAttribute("pageinationDTO",pageinationDTO);

            return "index";
    }
}
