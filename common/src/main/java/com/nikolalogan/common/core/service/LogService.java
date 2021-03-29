package com.nikolalogan.common.core.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 15:43
 */
public interface LogService {

    /**
     * 添加默认类型日志
     * @param operator 操作人
     * @param content 内容
     * @param logType 日志类型
     */
    void info(String operator,String content,Integer logType);

    /**
     * 添加日志
     * @param operator 操作人
     * @param status 状态
     * @param content 内容
     * @param logType 日志类型
     */
    void info(String operator,Integer status,String content,Integer logType);

    /**
     *
     * @param operator
     * @param status
     * @param content
     * @param logType
     * @param amount
     * @param businessTime
     * @param memo
     */
    void info(String operator, Integer status, String content, Integer logType, BigDecimal amount, Date businessTime,String memo);

}
