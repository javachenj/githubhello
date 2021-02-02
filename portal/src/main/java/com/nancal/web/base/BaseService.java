/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import com.nancal.common.enums.CodeEnum;
import com.nancal.common.exception.ServiceException;

import java.util.function.Function;

/**
 * @Description service统一异常拦截处理
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public class BaseService {

    public <T, R> MvcResult<R> apply(Function<T, R> fun, T t) {
        try {
            R r = fun.apply(t);
            return MvcResult.<R>builder()
                    .data(r)
                    .code(CodeEnum.OK.code())
                    .msg(CodeEnum.OK.msg())
                    .build();
        } catch (ServiceException e) {
            return MvcResult.<R>builder()
                    .code(e.getCode())
                    .msg(e.getMsg())
                    .build();
        } catch (Throwable e) {
            return MvcResult.<R>builder()
                    .code(CodeEnum.ERROR.code())
                    .msg(CodeEnum.ERROR.msg())
                    .build();
        }
    }
}
