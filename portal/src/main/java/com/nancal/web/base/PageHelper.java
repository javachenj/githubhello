/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

/**
 * @Classname PageHelper
 * @Description 查询分页参数，分页属性转换器类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public class PageHelper {

    public static PageRequest of(MvcTableReq req) {
        Sort sort = Sort.by(req.getSort()
                .getOrder()
                .stream()
                .map(item -> {
                    return item.isAsc()
                            ? Sort.Order.asc(item.getProperties())
                            : Sort.Order.desc(item.getProperties());
                }).collect(Collectors.toList()));
        return PageRequest.of(req.getPageNo(), req.getPageSize(), sort);
    }

}
