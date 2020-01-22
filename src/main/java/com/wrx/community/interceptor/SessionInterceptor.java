package com.wrx.community.interceptor;

import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import com.wrx.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    //    处理请求之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 查询cookie 中是否存在 token字段
                if (cookie.getName() != "" && cookie.getName().equals("token")) {
                    // 获取token中的信息
                    String token = cookie.getValue();
                    // 查库

                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);

                    List<User> users = userMapper.selectByExample(userExample);

                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                    }
                    break;
                }
            }

        }
        return true;
    }

    //    处理之后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    //    完成后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {

    }
}

