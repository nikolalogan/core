package com.nikolalogan.common.core.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 15:45
 */
public class LogDto extends BaseDto{

    /**
     * 日志类型
     */
    String logType;

    String logInfo;

    String logTime;

    /**
     * 操作人
     */
    String operator;

    /**
     * 收支类型
     */
    String balanceType;

    /**
     * 收支时间
     */
    Date businessTime;

    /**
     * 金额
     */
    BigDecimal amount;

    /**
     * 备注
     */
    String memo;

    /**
     * 图片
     */
    String file;
}
