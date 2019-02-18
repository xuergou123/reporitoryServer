package com.itmuch.cloud.study.user.entity;


import java.util.Map;

public class Result<T> {
    private Boolean success = false;
    private String msg = "操作成功";
    private Integer code = 0;
    private T data;

    public Result() {
    }

    private Result(Boolean success, String msg, Integer code, T data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result build(Boolean success, String msg, Integer code, Object data) {
        return new Result(success, msg, code, data);
    }

    public static Result build(Boolean success, String msg, Object data) {
        return build(success, msg, 0, data);
    }

    public static Result buildSuccess(String msg, Object data) {
        return build(Boolean.TRUE, msg, data);
    }

    public static Result buildFailure(Integer code, String msg, Object data) {
        return build(Boolean.FALSE, msg, code, data);
    }

    public static Result buildFailure(String msg, Object data) {
        return build(Boolean.FALSE, msg, data);
    }

    public static Result buildSuccess(String msg) {
        return buildSuccess(msg, (Object)null);
    }

    public static Result buildSuccess(Object data) {
        return buildSuccess((String)null, data);
    }

    public static Result buildFailure(String msg) {
        return buildFailure((String)msg, (Object)null);
    }

    public static Result buildFailure(Integer code, String msg) {
        return build(Boolean.FALSE, msg, code, (Object)null);
    }

    public static Result buildFailure(ResponseCode status) {
        return buildFailure(status.value(), status.getMessage());
    }

    public static Result buildFailure(ResponseCode status, Object data) {
        return buildFailure(status.value(), status.getMessage(), data);
    }

    public static Result buildFailure(Object data) {
        return buildFailure("", data);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setMsg(msg);
        return r;
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.setMsg(msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.setData(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }
}
