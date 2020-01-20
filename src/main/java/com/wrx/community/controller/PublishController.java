package com.wrx.community.controller;


import com.wrx.community.dto.QuestionDTO;
import com.wrx.community.model.Question;
import com.wrx.community.model.User;
import com.wrx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    // 处理 编辑 请求
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){

        QuestionDTO question = questionService.queryById(id);
        model.addAttribute("tittle",question.getTittle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        return "publish";
    }

    // 处理get请求
    @GetMapping("/publish")
    public String publish(HttpServletRequest req) {
        return "publish";
    }

    // 处理get请求
    @PostMapping("/publish")
    public String insertQuestion(
            @RequestParam(value = "tittle",required = false) String tittle,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest req,
            Model model
    ) {

        model.addAttribute("tittle", tittle);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        // 获取session中的用户数据
        User user = (User) req.getSession().getAttribute("user");

        if (user != null) {
            //如果获取到了 用户的数据

            // 三个判定条件
            if (tittle == "" || tittle == null) {
                model.addAttribute("errorMessage", "标题不能为空！");
                return "publish";
            }
            if (description == "" || description == null) {
                model.addAttribute("errorMessage", "问题补充不能为空！");
                return "publish";
            }
            if (tag == "" || tag == null) {
                model.addAttribute("errorMessage", "标签不能为空！");
                return "publish";
            }

            Question question = new Question();
            //设置相关参数
            question.setTittle(tittle);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreater(user.getId());
            question.setId(id);

            // questionService 判定是修改的 还是新增的 问题
            questionService.createOrUpdat(question);
            if (id != null){
                model.addAttribute("successMessage", "修改成功！");
            }else{
                model.addAttribute("successMessage", "发布成功！");
            }

            return "publish";

        } else {
            model.addAttribute("errorMessage", "用户未登录，请先登录！");
            // 如果获取不到 跳转至首页
            return "publish";
        }
    }

}
