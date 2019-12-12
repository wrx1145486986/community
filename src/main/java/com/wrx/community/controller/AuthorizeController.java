package com.wrx.community.controller;


import com.wrx.community.dto.AccessTokenDTO;
import com.wrx.community.dto.GithubUser;
import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import com.wrx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest req, HttpServletResponse resp) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //设置参数
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);

        // 获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (githubUser != null){
            // 当user不为空时表示登录成功

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            // 将用户数据插入数据库中
            userMapper.insert(user);

            // 写入cookie 呵session
            Cookie cookie = new Cookie("token", token);
            resp.addCookie(cookie);

            req.getSession().setAttribute("user", githubUser);
            return "redirect:/";

        }else{
            // 其他情况登录失败
            return "redirect:/";
        }
    }

}
