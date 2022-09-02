package com.neutech.enumeration;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CategoryStatusEnum {
    NORMAL(1,"正常"),
    DISCARD(2,"已废弃");

    public static CategoryStatusEnum getInstance(Integer statusCode){
        for(CategoryStatusEnum value:values()){
            if(value.statusCode.equals(statusCode)){
                return value;
            }
        }
        return null;
    }

    private Integer statusCode;
    private String statusMsg;

    CategoryStatusEnum(Integer statusCode,String statusMsg){
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
