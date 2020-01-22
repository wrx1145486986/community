package com.wrx.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("所查询的问题不存在了，换个试试！"),
    QUESTION_NOT_UPDATE("问题更新失败，请稍后重试！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
