package com.wrx.community.mapper;

import com.wrx.community.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExtMapper {

    int incView(Question record);
}