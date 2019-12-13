package com.wrx.community.service;

import com.wrx.community.dto.QuestionDTO;
import com.wrx.community.mapper.QuestionMapper;
import com.wrx.community.mapper.UserMapper;
import com.wrx.community.model.Question;
import com.wrx.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    // 将question中的数据 与 user 中的数据整合到一块
    public List<QuestionDTO> queryAllQuestion() {

        List<Question> questionList = questionMapper.queryAllQuestion();

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList){

            User user = userMapper.queryById(question.getCreater());

            QuestionDTO qdto = new QuestionDTO();

//          Spring 中的工具类  将question对象中的所有属性 copy 到 qdto 对象中去
            BeanUtils.copyProperties(question,qdto);
            qdto.setUser(user);

            questionDTOList.add(qdto);

        }
        return questionDTOList;
    }
}
