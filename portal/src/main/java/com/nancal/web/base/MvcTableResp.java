/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description 分页查询返回结果数据封装
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@Builder
public class MvcTableResp <T> {
    @ApiModelProperty(value = "总记录数", example = "1000")
    private long total;
    @ApiModelProperty(name = "响应码", example = "0")
    private int code;
    @ApiModelProperty(value = "列表")
    private List<T> data;
}