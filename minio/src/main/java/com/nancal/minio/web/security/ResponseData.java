///**
//* @Title: ResponseData.java
//* @Package com.tecsun.common.response
//* @Description:
//* @author admin
//* @date 2020年5月30日 下午3:08:28
//* @version V1.0
//* @Copyright: 2020 www.tecsun.com Inc. All rights reserved.
//*/
//package com.nancal.minio.web.security;
//
//
//import com.nancal.common.enums.CodeEnum;
//
//import java.io.Serializable;
//
///**
// * 响应数据体
// *
// * @author Lzy 2020-01-13
// */
//public class ResponseData extends BaseResponse implements Serializable {
//
//    /**
//    * @Fields serialVersionUID  描述：
//    */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 数据
//     */
//    private Object data;
//
//    private ResponseData() {
//    }
//
//    private ResponseData(CodeEnum code) {
//        super(code);
//    }
//
//    private ResponseData(CodeEnum code, Object data) {
//        super(code);
//        this.data = data;
//    }
//
//    private ResponseData(Integer code, String msg, Object data) {
//        super(code, msg);
//        this.data = data;
//    }
//
//    private ResponseData(Integer code, String msg) {
//        super(code, msg);
//    }
//
//    /**
//     * 对外开放基础响应体已供调用，可用于增、删、改接口操作
//     */
//    public static ResponseData out(CodeEnum code) {
//        return new ResponseData(code);
//    }
//
//    /**
//     * 对外开放基础响应体已供调用，可用于增、删、改接口操作
//     */
//    public static ResponseData out(Integer code, String msg) {
//        return new ResponseData(code, msg);
//    }
//
//    /**
//     * 对外开放数据响应体已供调用，可用于查询数据实用，引用了范型设计，支持各种数据类型
//     */
//    public static ResponseData out(CodeEnum code, Object data) {
//        return new ResponseData(code, data);
//    }
//
//    /**
//     * 对外开放数据响应体已供调用，可用于查询数据实用，引用了范型设计，支持各种数据类型
//     */
//    public static ResponseData out(Integer code, String msg, Object data) {
//        return new ResponseData(code, msg, data);
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//}
