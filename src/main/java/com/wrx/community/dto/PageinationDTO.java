package com.wrx.community.dto;

import lombok.Data;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageinationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPageination(Integer totalPage, Integer pageNum) {

        this.totalPage = totalPage;
        this.page = pageNum;

//        对于特殊page的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pages.add(page);

        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }


        if (page == 1) {
            showFirstPage = false;
            showPrevious = false;
        } else {
            showFirstPage = true;
            showPrevious = true;
        }

        if (page == totalPage) {
            showEndPage = false;
            showNext = false;
        } else {
            showEndPage = true;
            showNext = true;
        }
    }
}
