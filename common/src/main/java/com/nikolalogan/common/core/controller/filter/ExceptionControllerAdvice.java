package com.nikolalogan.common.core.controller.filter;

import com.nikolalogan.common.core.controller.exception.APIException;
import com.nikolalogan.common.core.controller.response.Resp;
import com.nikolalogan.common.core.controller.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2020-09-08 19:51
 */

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(APIException.class)
    public Resp<String> APIExceptionHandler(APIException e) {
        // 注意哦，这里传递的响应码枚举
        log.error(e.getMessage());
        return new Resp<>(ResultCode.FAILED, e.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Resp<String> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuffer error = new StringBuffer();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            error.append(constraintViolation.getMessageTemplate()).append("|");
        }
        log.error(String.valueOf(error));
        return new Resp<>(ResultCode.FAILED, String.valueOf(error));
    }
    @ExceptionHandler(BindException.class)
    public Resp<String> BindExceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuffer error = new StringBuffer();
        for (ObjectError errOb : allErrors) {
            error.append(errOb.getDefaultMessage()).append("|");
        }
        log.error(String.valueOf(error));
        return new Resp<>(ResultCode.FAILED, String.valueOf(error));
    }
//    @ExceptionHandler(Exception.class)
//    public R<String> Exception(Exception e) {
//        log.error(e.getMessage());
//        return new R<>(ResultCode.FAILED, e.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        log.error(e.getMessage());
        return new Resp<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }
}