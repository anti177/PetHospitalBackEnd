package com.example.pethospitalbackend.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(200, "成功"),

    FAIL(1, "失败"),

    ILLEGAL_PARAM(101, "参数错误"),
    TEL_OR_PWD_ERROR(102, "邮箱或密码错误"),
    VERIFY_MSG_CODE_INVALID(103, "验证码已失效，请重试"),
    VERIFY_MSG_CODE_VALID(104,"验证码已发送，请查收"),
    USER_NOT_FOUND(105, "未找到对应的用户"),
    SERVER_ERROR(500, "服务器内部错误")
    ;

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
