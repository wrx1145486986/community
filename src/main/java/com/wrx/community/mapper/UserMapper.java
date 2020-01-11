package com.wrx.community.mapper;

import com.wrx.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    // 新增加用户
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values(#{name}," +
            "#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    // 根据数据库中的token 查找用户
    @Select("select id,name, account_id accountId,token,gmt_create gmtCreate," +
            "gmt_modified gmtModified,bio,avatar_url avatarUrl from user where token = #{token}")
    User findByToken(@Param("token") String token);

    // 根据 id 查找用户
    @Select("select * from user where id = #{id}")
    User queryById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{account_id}")
    User queryByAccountId(@Param("account_id") String accountId);

}
