package com.nikolalogan.common.core.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2020-09-08 21:13
 */

@Getter
@Setter
public class Resp<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private int code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    /**
     * 其他信息
     */
    private Map<String, Object> map = Map.of();

    /**
     * 总记录数(layui table)
     */
    private Long count = 0L;
    /**
     * 列表数据（layui table)
     */
    private List<?> list = List.of();


    public Resp(T data) {
        this(ResultCode.SUCCESS, String.valueOf(data));
    }

    public Resp(ResultCode resultCode, String msg) {
        this.code = resultCode.getCode();
        this.msg = msg;
    }


    public Resp(ResultCode resultCode, String msg, Map<String, Object> map) {
        this.code = code;
        this.msg = msg;
        this.map = map;
    }

    public Resp(ResultCode resultCode, String msg, Long count, List<?> list) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.list = list;
    }

   /* public static Resp to(ResultCode resultCode, String message) {
        return new Resp(resultCode, message);
    }

    public static Resp success() {
        return to(ResultCode.SUCCESS, "操作成功");
    }

    public static Resp success(String message) {
        return to(ResultCode.SUCCESS, message);
    }

    public static Resp failed(String message) {
        return to(ResultCode.FAILED, message);
    }

    public static Resp error(String message) {
        return to(ResultCode.ERROR, message);
    }
*/
}