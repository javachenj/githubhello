/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.common.enums;

/**
 * @Description 响应码枚举类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public enum CodeEnum {

    OK(0, "成功"),

    ERROR(-1, "系统异常");

    private int code;

    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code(){
        return code;
    }

    public String msg() {
        return msg;
    }
}
