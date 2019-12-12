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
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio) values(#{name}," +
            "#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio})")
    public void insert(User user);

    // 根据数据库中的token 查找用户
    @Select("select id id,name name, account_id accountId,token token,gmt_create gmtCreate," +
            "gmt_modified gmtModified,bio bio from user where token = #{token}")
    public User findByToken(@Param("token") String token);

}
