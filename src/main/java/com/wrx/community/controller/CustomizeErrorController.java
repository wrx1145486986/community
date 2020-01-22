package com.wrx.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    public ModelAndView errorHtml(HttpServletRequest request, Model model) {

        HttpStatus status = getStatus(request);

//        两个判定条件
        if (status.is4xxClientError()){
            model.addAttribute("message","请求存在问题！");
        }
        if (status.is5xxServerError()){
            model.addAttribute("message","服务器繁忙请稍后重试！！！");

        }

        return new ModelAndView("error");
    }

//    spring 源码
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");

        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
