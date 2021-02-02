/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.uc.service.impl;

import com.nancal.common.enums.CodeEnum;
import com.nancal.common.exception.ServiceException;
import com.nancal.entity.uc.QUser;
import com.nancal.entity.uc.User;
import com.nancal.model.uc.UserModel;
import com.nancal.web.base.*;
import com.nancal.web.uc.dao.UserDao;
import com.nancal.web.uc.service.UserService;
import com.nancal.web.uc.service.predicate.UserPredicates;
import com.nancal.web.uc.service.queryform.UserQueryForm;
import com.nancal.web.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @Description 用户中心模块-用户管理service实现类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private QUser entity = QUser.user;

    @Autowired
    private UserDao userDao;

    @Override
    public MvcTableResp<UserModel> findPage(MvcTableReq<UserQueryForm> req) {
        Page<User> result = userDao.findAll(UserPredicates.findByForm(req.getData()), PageHelper.of(req));
        return MvcTableResp.<UserModel>builder()
            .total(result.getTotalElements())
            .data(result.getContent().stream().map(item -> {
                UserModel userModel = new UserModel();
                CopyUtils.objectToObject(item, userModel);
                return userModel;
            }).collect(Collectors.toList())).build();
    }

    @Override
    public MvcResult<UserModel> findOne(Long id) {
        return apply((item) -> {
            User user = userDao.findOne(entity.id.eq(id))
                    .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "用户不存在！"));
            UserModel userModel = new UserModel();
            CopyUtils.objectToObject(user, userModel);
            return userModel;
        }, id);
    }

    @Override
    public MvcResult<UserModel> create(UserModel userModel) {
        return apply((item) -> {
            User user = new User();
            CopyUtils.objectToObject(userModel, user);
            userDao.save(user);
            return userModel;
        }, userModel);
    }

    @Transactional
    @Override
    public MvcResult<UserModel> update(UserModel userModel) {
        return apply((item) -> {
            userDao.findOne(entity.id.eq(item.getId()))
                    .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "用户不存在！"));
            User user = new User();
            CopyUtils.objectToObject(userModel, user);
            userDao.save(user);
            return userModel;
        }, userModel);

    }

    @Transactional
    @Override
    public MvcResult<Boolean> delete(Long id) {
        return apply((i) -> {
            User u = userDao.findOne(entity.id.eq(i))
                    .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "用户不存在！"));
            userDao.delete(u);
            return Boolean.TRUE;
        }, id);
    }

}
