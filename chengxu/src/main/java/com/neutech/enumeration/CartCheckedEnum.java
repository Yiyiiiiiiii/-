package com.neutech.enumeration;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  CartCheckedEnum {

    NO_CHECKED(0,"未勾选"),
    HAS_BEAN_CHECKED(1,"已勾选");

    //传入一个数字，通过数字得到枚举对象
    //statusCode 传入的数字
    //return 枚举对象或者null
    public static CartCheckedEnum getInstance(Integer statusCode){
        for(CartCheckedEnum value:values()){
            if(value.statusCode.equals(statusCode)){
                return value;
            }
        }
        return null;
    }

    private Integer statusCode;
    private String statusMsg;

    CartCheckedEnum(Integer statusCode,String statusMsg){
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
