package com.neutech.enumeration;

public enum ResultErrorEnum {
    PARAM_FORMAT_ERROR(1001,"参数格式不对"),
    PARAM_WRONGFUL(1002,"参数不合法"),
    NO_FILE_ERROR(1011,"没有文件"),
    UPLOAD_FAIL(1012,"上传错误"),
    USERNAME_NOT_EXISTS(3001,"用户名不存在"),
    PASSWORD_INCORRECT(3002,"密码不正确"),
    NOT_LOGGED_IN(3003,"用户未登录");

    private Integer errorCode;
    private String errorMsg;

    ResultErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
