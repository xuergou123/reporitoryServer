package com.itmuch.cloud.study.user.entity;

public enum ResponseCode {
    status_ok(200, "get:表示已在响应中发出;delete:资源已被删除;put,post:如果已存在资源被更改"),
    status_empty(204, "无内容"),
    status_paramerror(400, "参数错误"),
    status_nodata(404, "资源不存在"),
    status_commonerror(500, "通用错误码");

    private final int value;
    private final String message;

    private ResponseCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int value() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }
}
