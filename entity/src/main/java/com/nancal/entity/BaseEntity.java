/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 实体基本字段定义
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {

    /** 主键ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.nancal.common.utils.IdGenerator")
    protected Long id;
    /** 创建时间 */
    @Column(name = "create_at")
    protected LocalDateTime createAt;
    /** 更新时间 */
    @Column(name = "update_at")
    protected LocalDateTime updateAt;
    /** 创建者ID */
    @Column(name = "create_user_id")
    protected String createUserId;
    /** 创建者账号或者名称 */
    @Column(name = "create_by")
    protected String createBy;
    /** 更新者ID */
    @Column(name = "update_user_id")
    protected String updateUserId;
    /** 更新者账号或者名称 */
    @Column(name = "update_by")
    protected String updateBy;
    /** 是否删除. */
    @Column(name = "del_flag")
    protected boolean delFlag;

    public BaseEntity(Long id, LocalDateTime createAt, LocalDateTime updateAt, String createUserId, String createBy, String updateUserId, String updateBy, boolean delFlag) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createUserId = createUserId;
        this.createBy = createBy;
        this.updateUserId = updateUserId;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
    }
}
