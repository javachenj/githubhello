/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description 全局统一返回结果处理
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class MvcResult <T> {
    @ApiModelProperty(name = "响应码", example = "0")
    private int code;
    @ApiModelProperty(name = "响应描述")
    private String msg;
    @ApiModelProperty(name = "响应对象")
    private T data;
}
