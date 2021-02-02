/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 封装排序字段
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MvcSort {
    /** 排序字段 */
    private List<MvcOrder> order = new ArrayList<>();
}
