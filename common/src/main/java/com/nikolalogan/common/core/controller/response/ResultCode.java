package com.nikolalogan.common.core.controller.response;

import lombok.Getter;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2020-09-08 21:13
 */

@Getter
public enum ResultCode {
    NO_AUTHORITY(900, "无权限"),
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    ERROR(5000, "未知错误");

    private int code;
    private String msg;


    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
