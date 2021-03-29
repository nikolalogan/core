package com.nikolalogan.common.core.utils;


import com.nikolalogan.common.core.controller.response.Resp;
import com.nikolalogan.common.core.controller.response.ResultCode;
import com.nikolalogan.common.core.dto.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 响应消息工具类
 */
public class R {

    /**
     * 基本
     *
     * @param code 状态码
     * @param msg  提示信息
     * @return
     */
    public static Resp to(ResultCode code, String msg) {
        return new Resp(code, msg);
    }

    public static Resp success() {
        return to(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg());
    }

    public static Resp success(String msg) {
        return to(ResultCode.SUCCESS, msg);
    }

    public static Resp failed(String msg) {
        return to(ResultCode.FAILED, msg);
    }

    public static Resp error(String msg) {
        return to(ResultCode.ERROR, msg);
    }

    /**
     * 携带信息
     *
     * @param code 状态码
     * @param msg  提示信息
     * @param map  其他信息
     * @return
     */
    public static Resp to(ResultCode code, String msg, Map<String, Object> map) {
        return new Resp(code, msg, map);
    }

    public static Resp success(Map<String, Object> map) {
        return to(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg(), map);
    }

    public static Resp success(String msg, Map<String, Object> map) {
        return to(ResultCode.SUCCESS, msg, map);
    }

    public static Resp failed(String msg, Map<String, Object> map) {
        return to(ResultCode.FAILED, msg, map);
    }

    public static Resp error(String msg, Map<String, Object> map) {
        return to(ResultCode.ERROR, msg, map);
    }

    /**
     * 分页结果（表格数据）
     *
     * @param code 状态码
     * @param msg  提示信息
     * @param page 表格数据
     * @return
     */
    public static Resp to(ResultCode code, String msg, PageResult page) {
        return new Resp(code, msg, page.getTotal(), page.getRows());
    }

    /**
     * 分页结果（表格数据）
     *
     * @param page 表格数据
     * @return
     */
    public static Resp to(PageResult page) {
        return to(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg(), page);
    }

    /**
     * 列表结果（列表数据）
     *
     * @param code     状态码
     * @param msg      提示信息
     * @param listData 列表数据
     * @return
     */
    public static Resp to(ResultCode code, String msg, List<?> listData) {
        return new Resp(code, msg, Long.valueOf(listData.size()), listData);
    }

    /**
     * 列表结果（列表数据）
     *
     * @param listData
     * @return
     */
    public static Resp to(List<?> listData) {
        return to(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg(), listData);
    }
}
