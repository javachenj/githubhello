/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.common.enums;

/**
 * @Description 数据字典枚举类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public enum DictTypeEnum {
    DATASOURCE_DIRECTION("数据源方向"),
    DATASOURCE_MODE("数据源模式"),
    DATABASE_TYPE("数据库类型");
    String name;
    DictTypeEnum(String name) {
        this.name = name;
    }
}
