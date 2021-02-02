/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.uc.dao;

import com.nancal.entity.uc.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Description 用户中心模块-用户管理DAO
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Repository
public interface UserDao extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {

}
