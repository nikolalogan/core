package com.nikolalogan.common.core.controller.exception;

import com.nikolalogan.common.core.controller.response.ResultCode;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2020-09-08 21:11
 */
public class APIException extends RuntimeException {
    private ResultCode code;
    private String msg;

    public APIException() {
        this(ResultCode.ERROR, "接口错误");
    }

    public APIException(String msg) {
        this(ResultCode.FAILED, msg);
    }

    public APIException(ResultCode code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}