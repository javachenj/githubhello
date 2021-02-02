/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description service异常类统一定义
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@ToString
@EqualsAndHashCode
public class ServiceException extends RuntimeException {
    private int code;
    private String msg;

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

}
