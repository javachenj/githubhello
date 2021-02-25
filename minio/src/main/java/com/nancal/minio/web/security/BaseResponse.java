//package com.nancal.minio.web.security;
//
///**
// * 响应数据基类
// * @author liuyang
// * 2020-01-13
// */
//public class BaseResponse {
//
//    /**
//     * 状态码
//     */
//    private Integer code;
//
//    /**
//     * 状态描述
//     */
//    private String msg;
//
//    protected BaseResponse() {
//    }
//
//    protected BaseResponse(CodeEnum code) {
//        this.code = code.getCode();
//        this.msg = code.getMsg();
//    }
//
//    protected BaseResponse(Integer code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//}
