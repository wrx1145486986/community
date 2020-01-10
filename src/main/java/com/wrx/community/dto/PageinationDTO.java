package com.wrx.community.dto;

import lombok.Data;
import org.slf4j.Logger;

import java.util.List;

@Data
public class PageinationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages;

    public void setPageination(Integer totalCount, Integer pageNum, Integer size) {

       Integer totalPage = 0;
        if (totalCount % size > 0){
            totalPage = totalCount / size +1;
        }else{
            totalPage = totalCount / size;
        }

        if (page == 1){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        if (page == totalPage){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
