/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.entity.uc;

import com.nancal.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 用户entity定义
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "demo_user")
public class User extends BaseEntity {

    /** 用户名称 */
    @Column(name = "name")
    private String name;

    /** 用户性别 */
    @Column(name = "sex")
    private Integer sex;

    /** 手机号 */
    @Column(name = "cellphone")
    private String cellphone;

    /** 邮箱号 */
    @Column(name = "email")
    private String email;

    /** 家庭住址 */
    @Column(name = "family_address")
    private String familyAddress;

    @Builder
    public User(Long id, LocalDateTime createAt, LocalDateTime updateAt, String createUserId, String createBy, String updateUserId, String updateBy, boolean delFlag, String name, Integer sex, String cellphone, String email, String familyAddress) {
        super(id, createAt, updateAt, createUserId, createBy, updateUserId, updateBy, delFlag);
        this.name = name;
        this.sex = sex;
        this.cellphone = cellphone;
        this.email = email;
        this.familyAddress = familyAddress;
    }
}
