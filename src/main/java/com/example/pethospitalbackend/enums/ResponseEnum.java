package com.example.pethospitalbackend.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
  SUCCESS(200, "成功"),

  FAIL(1, "失败"),

  SERVER_ERROR(500, "服务器内部错误"),

  ILLEGAL_PARAM(101, "参数错误"),
  TEL_OR_PWD_ERROR(102, "邮箱或密码错误"),
  VERIFY_MSG_CODE_INVALID(103, "验证码已失效，请重试"),
  VERIFY_MSG_CODE_VALID(104, "验证码已发送，请查收"),
  USER_NOT_FOUND(105, "用户未注册"),
  MAIL_HAS_REGISTERED(106, "邮箱已经注册"),
  VERIFY_MSG_CODE_OR_MAIL_INVALID(107, "验证码过期或者邮箱错误"),
  VERIFY_INVALID(108, "验证过期,请重新登陆"),
  UPLOAD_OSS_FAILURE(109, "上传文件失败"),
  DATABASE_FAIL(110, "数据库操作失败"),
  SEND_MAIL_FAIL(111, "发送邮件失败"),

  CONFLICT(409, "资源发生冲突");

  private final int code;
  private final String msg;

  ResponseEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
