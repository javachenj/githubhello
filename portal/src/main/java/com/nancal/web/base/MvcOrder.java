/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import lombok.*;

/**
 * @Description 分页排序封装类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MvcOrder {
    /** 属性名.  */
    private String properties;

    /** 是否正序. */
    private boolean asc;
}
