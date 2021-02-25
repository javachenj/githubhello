package com.nancal.web.uc.dao;

import com.nancal.entity.uc.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTestDao extends JpaRepository<UserTest,Long>, QuerydslPredicateExecutor<UserTest> {
}
