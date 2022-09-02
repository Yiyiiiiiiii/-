package com.neutech.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProductStatusEnum {
    ON_SALE(1,"在售"),
    OFF_THE_SHELF(2,"下架"),
    DELETED(3,"删除");

    //传入一个数字，通过数字得到枚举对象
    //statusCode 传入的数字
    //return 枚举对象或者null
    public static ProductStatusEnum getInstance(Integer statusCode){
        for(ProductStatusEnum value:values()){
            if(value.statusCode.equals(statusCode)){
                return value;
            }
        }
        return null;
    }

    private Integer statusCode;
    private String statusMsg;

    ProductStatusEnum(Integer statusCode,String statusMsg){
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
