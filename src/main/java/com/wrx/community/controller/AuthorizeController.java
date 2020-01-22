package com.wrx.community.controller;


import com.wrx.community.dto.AccessTokenDTO;
import com.wrx.community.dto.GithubUser;
import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.User;
import com.wrx.community.model.UserExample;
import com.wrx.community.provider.GithubProvider;
import com.wrx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
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

        if (githubUser != null && githubUser.getId() != null) {
            // 当user不为空时表示登录成功

//            查询数据库中 没有这个人员时 进行 进行新增操作

            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(githubUser.getId().toString());
            List<User> users = userMapper.selectByExample(example);
            User queryUser = (User) users.get(0);

            String token = UUID.randomUUID().toString();
            queryUser.setToken(token);

            userService.createOrUpdate(queryUser);

            Cookie cookie = new Cookie("token", queryUser.getToken());
            resp.addCookie(cookie);

            req.getSession().setAttribute("user", githubUser);

        }
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest req,HttpServletResponse resp){

//      清除 session
        req.getSession().removeAttribute("user");
//      清除 cookie
        Cookie cookie = new Cookie("token","null");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        return "redirect:/";
    }

}
