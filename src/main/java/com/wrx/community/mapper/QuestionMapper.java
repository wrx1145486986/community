package com.wrx.community.mapper;

import com.wrx.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

}
