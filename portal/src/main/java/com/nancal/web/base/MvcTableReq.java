/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 分页参数接收器
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
public class MvcTableReq<T> {
    @ApiModelProperty(name = "分页页码", example = "0")
    private int pageNo;
    @ApiModelProperty(name = "分页大小", example = "10")
    private int pageSize;
    @ApiModelProperty(name = "查询条件")
    private T data;
    @ApiModelProperty(name = "排序")
    private MvcSort sort;

}
