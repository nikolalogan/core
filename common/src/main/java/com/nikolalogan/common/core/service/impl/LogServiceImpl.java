package com.nikolalogan.common.core.service.impl;

import cn.hutool.json.JSONUtil;
import com.nikolalogan.common.core.dto.LogDto;
import com.nikolalogan.common.core.reponsitory.dao.LogDao;
import com.nikolalogan.common.core.service.LogService;
import com.nikolalogan.common.core.reponsitory.entity.BaseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 15:52
 */
@Slf4j
@Service
public class LogServiceImpl extends IBaseServiceImpl<LogDao,LogDto, BaseLog> implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public void info(String operator, String content, Integer logType) {
        BaseLog baseLog = new BaseLog(operator,BaseLog.DEFAULT,content,logType);
        log.info("记录日志：{}", JSONUtil.toJsonStr(baseLog));
        this.logDao.saveOrUpdate(baseLog);
    }

    @Override
    public void info(String operator, Integer status, String content, Integer logType) {
        BaseLog baseLog = new BaseLog(operator,status,content,logType);
        log.info("记录日志：{}", JSONUtil.toJsonStr(baseLog));
        this.logDao.saveOrUpdate(baseLog);
    }

    @Override
    public void info(String operator, Integer status, String content, Integer logType, BigDecimal amount, Date businessTime, String memo) {
        BaseLog baseLog = new BaseLog(operator,status,content,logType,amount,businessTime,memo);
        log.info("记录日志：{}", JSONUtil.toJsonStr(baseLog));
        this.logDao.saveOrUpdate(baseLog);
    }
}
