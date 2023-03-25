package com.example.pethospitalbackend.response;

import com.example.pethospitalbackend.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


@Api("请求通用返回数据模型")
@Data
@ToString
public class Response<T> {

    @ApiModelProperty("请求是否成功 仅代表请求是否成功 无业务相关含义")
    private boolean success;

    @ApiModelProperty("返回状态码")
    private int status;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据格式")
    private T result;

    public void setSuc(T data) {
        setSuccess(true);
        setStatus(ResponseEnum.SUCCESS.getCode());
        setMessage(ResponseEnum.SUCCESS.getMsg());
        setResult(data);
    }

    public void setFail(String msg) {
        setSuccess(false);
        setStatus(ResponseEnum.FAIL.getCode());
        setMessage(msg);
        setResult(null);
    }

    public void setFail(int code, String msg) {
        setSuccess(false);
        setStatus(code);
        setMessage(msg);
        setResult(null);
    }

    public void setFail(ResponseEnum responseStatus) {
        setSuccess(false);
        setStatus(responseStatus.getCode());
        setMessage(responseStatus.getMsg());
        setResult(null);
    }
}

