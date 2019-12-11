package com.wrx.community.provider;

import com.alibaba.fastjson.JSON;
import com.wrx.community.dto.AccessTokenDTO;
import com.wrx.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

// 注入到spring上下文
@Component
public class GithubProvider {


    // 获取 accessToken
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        //伪造请求体 将accessTonken与媒体类型伪造请求
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //String 分割获取 access_token
            String[] split = string.split("&")[0].split("=");
            return split[1];
        }catch(Exception e){
            System.out.println("GithubProvider.class getAccessToken 发生异常！");
        }

        return null;
    }

    //通过 accessToken 从github上拉取用户数据
    public GithubUser getUser(String accesstoken){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();

        try (Response response = client.newCall(request).execute()) {
             String githubresponce = response.body().string();
             // 将 github的返回数据转成json格式 并自动解析成 githubUser格式
            GithubUser githubUser = JSON.parseObject(githubresponce, GithubUser.class);
            return githubUser;
        }catch (Exception e){
            System.out.println("GithubProvider.class getUser 发生异常！");
        }

        return null;
    }

}
