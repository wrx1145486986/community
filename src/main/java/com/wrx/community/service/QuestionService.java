package com.wrx.community.service;

import com.wrx.community.dto.PageinationDTO;
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


    // 将question中的数据 与 user 中的数据整合到一块 page 默认为 1    size 默认为 5
    public PageinationDTO queryQuestion(Integer page, Integer size) {


        PageinationDTO pageinationDTO = new PageinationDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //        对于特殊page的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDTO.setPageination(totalPage,page);

        Integer pageNum = (page - 1) * size;

//        查询问题
        List<Question> questionList = questionMapper.queryQuestion(pageNum, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
//          根据question中的Creatyer 查找user表中的符合这个数据的id
            User user = userMapper.queryById(question.getCreater());

            QuestionDTO qdto = new QuestionDTO();
//          Spring 中的工具类  将question对象中的所有属性 copy 到 qdto 对象中去
            BeanUtils.copyProperties(question, qdto);
            qdto.setUser(user);

            questionDTOList.add(qdto);

        }

        pageinationDTO.setQuestions(questionDTOList);

        pageinationDTO.setPage(page);


        return pageinationDTO;
    }


    public PageinationDTO list(Integer userId, Integer page, Integer size) {

        PageinationDTO pageinationDTO = new PageinationDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //        对于特殊page的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDTO.setPageination(totalPage, page);

        Integer pageNum = (page - 1) * size;

//        查询问题
        List<Question> questionList = questionMapper.queryQuestionByUserId(userId, pageNum, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
//          根据question中的Creatyer 查找user表中的符合这个数据的id
            User user = userMapper.queryById(question.getCreater());

            QuestionDTO qdto = new QuestionDTO();
//          Spring 中的工具类  将question对象中的所有属性 copy 到 qdto 对象中去
            BeanUtils.copyProperties(question, qdto);
            qdto.setUser(user);

            questionDTOList.add(qdto);

        }

        pageinationDTO.setQuestions(questionDTOList);

        pageinationDTO.setPage(page);


        return pageinationDTO;

    }
}
