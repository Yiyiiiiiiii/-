package com.neutech.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/*
* 服务器端统一返回
* */
public class ResultVO {
    private Integer code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static ResultVO success(){
        ResultVO resultVO =new ResultVO();
        resultVO.code=0;
        resultVO.message="成功";
        return resultVO;
    }

    public static ResultVO success(Object data){
        ResultVO resultVO =new ResultVO();
        resultVO.code=0;
        resultVO.message="成功";
        resultVO.data=data;
        return resultVO;
    }
    public static ResultVO error(Integer code,String message){
        ResultVO resultVO =new ResultVO();
        resultVO.code=code;
        resultVO.message=message;
        return resultVO;
    }
    public ResultVO(){

    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
