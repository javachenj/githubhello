/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.uc.service.predicate;

import com.nancal.entity.uc.QUser;
import com.nancal.web.uc.service.queryform.UserQueryForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.StringUtils;

/**
 * @Description 用户中心模块-用户多条件封装类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public class UserPredicates {

    public static Predicate findByForm(UserQueryForm queryForm) {
        QUser entity = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(queryForm.getName())) {
            builder.and(entity.name.like("%" + queryForm.getName() + "%"));
        }
        return builder;
    }
}
