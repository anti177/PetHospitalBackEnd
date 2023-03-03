package com.example.pethospitalbackend.Response;

import com.example.pethospitalbackend.Enums.ResponseEnum;
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
    private int code;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("返回数据格式")
    private T result;

    public void setSuc(T data) {
        setSuccess(true);
        setCode(ResponseEnum.SUCCESS.getCode());
        setMsg(ResponseEnum.SUCCESS.getMsg());
        setResult(data);
    }

    public void setFail(String msg) {
        setSuccess(false);
        setCode(ResponseEnum.FAIL.getCode());
        setMsg(msg);
        setResult(null);
    }

    public void setFail(int code, String msg) {
        setSuccess(false);
        setCode(code);
        setMsg(msg);
        setResult(null);
    }

    public void setFail(ResponseEnum responseStatus) {
        setSuccess(false);
        setCode(responseStatus.getCode());
        setMsg(responseStatus.getMsg());
        setResult(null);
    }
}

