package com.wrx.community.mapper;

import com.wrx.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    //    写入question数据
    @Insert("insert into question (tittle,description,gmt_create," +
            "gmt_modified,creater,comment_count," +
            "view_count,like_count,tag ) values(#{tittle}," +
            "#{description},#{gmtCreate},#{gmtModified},#{creater},#{commentCount}," +
            "#{viewCount},#{likeCount},#{tag})")
    void insertQuestion(Question question);

    //    分页查询 question
    @Select("select * from question limit #{pageNum} , #{size}")
    List<Question> queryQuestion(@Param(value = "pageNum") Integer pageNum, @Param(value = "size") Integer size);

    //    查询问题个数
    @Select("select count(*) from question")
    Integer count();

    @Select("select * from question where creater = #{userId} limit #{pageNum}, #{size}")
    List<Question> queryQuestionByUserId(@Param(value = "userId") Integer userId, @Param(value = "pageNum") Integer pageNum, @Param(value = "size") Integer size);

    @Select("select Count(*) from question where creater = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question queryById(@Param(value = "id") Integer id);

    @Update("update question set tittle = #{tittle}, description = #{description},gmt_modified = #{gmtModified}," +
            "tag = #{tag} where id = #{id}")
    void update(Question question);
}
