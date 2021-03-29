package com.nikolalogan.common.core.reponsitory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 14:07
 */
@Setter
@Getter
@Entity
@Table(name = "log")
public class BaseLog extends BaseEntity {
    /**
     * 默认类型
     */
    public static final int DEFAULT = 0;

    /**
     * 收支
     */
    public static final int FINANCE = 1;

    /**
     * 人员
     */
    public static final int USER = 2;

    /**
     * 房屋
     */
    public static final int HOUSE =3;

    /**
     * 合同
     */
    public static final int CONTRACT=4;

    public static final int SUCCESS = 0;

    public static final int FAILED = 1;

    public static final int WARNING = 2;


    /**
     * 日志类型
     */
    @Column
    Integer logType;

    @Column
    String logInfo;

    @Column
    Integer logStatus;

    /**
     * 操作人
     */
    @Column
    String operator;

    /**
     * 收支类型
     */
    @Column
    String balanceType;

    /**
     * 收支时间
     */
    @Column
    Date businessTime;

    /**
     * 金额
     */
    @Column
    BigDecimal amount;

    /**
     * 备注
     */
    @Column
    String memo;

    /**
     * 图片
     */
    @OneToMany(fetch = FetchType.LAZY)
    List<FileEntity> file;

    public BaseLog(){

    }

    public BaseLog(String operator, Integer status, String content, Integer logType){
        this.operator = operator;
        this.logStatus = status;
        this.logInfo = content;
        this.logType = logType;
    }
    public BaseLog(String operator, Integer status, String content, Integer logType,BigDecimal amount, Date businessTime, String memo){
        this.operator = operator;
        this.logStatus = status;
        this.logInfo = content;
        this.logType = logType;
        this.amount = amount;
        this.businessTime = businessTime;
        this.memo = memo;
    }
}
