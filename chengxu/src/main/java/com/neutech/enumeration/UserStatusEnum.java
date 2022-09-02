package com.neutech.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  UserStatusEnum {
    BOSS(0,"管理者"),
    COMMONUSER(1,"普通用户");


    public static UserStatusEnum getInstance(Integer statusCode){
        for(UserStatusEnum value:values()){
            if(value.statusCode.equals(statusCode)){
                return value;
            }
        }
        return null;
    }

    private Integer statusCode;
    private String statusMsg;

    UserStatusEnum(Integer statusCode,String statusMsg){
        this.statusCode=statusCode;
        this.statusMsg=statusMsg;
    }
    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }
    }
