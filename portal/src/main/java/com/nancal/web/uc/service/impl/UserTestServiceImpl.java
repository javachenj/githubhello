package com.nancal.web.uc.service.impl;

import com.nancal.common.enums.CodeEnum;
import com.nancal.common.exception.ServiceException;
import com.nancal.entity.uc.QUserTest;
import com.nancal.entity.uc.UserTest;
import com.nancal.model.uc.UserTestModel;
import com.nancal.web.base.*;
import com.nancal.web.uc.dao.UserTestDao;
import com.nancal.web.uc.service.UserTestService;
import com.nancal.web.uc.service.predicate.UserTestPredicates;
import com.nancal.web.uc.service.queryform.UserTestQueryForm;
import com.nancal.web.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserTestServiceImpl extends BaseService implements UserTestService {
    @Autowired
    private UserTestDao userTestDao;

    @Override
    public MvcTableResp<UserTestModel> page(MvcTableReq<UserTestQueryForm> req) {
        Page<UserTest> users = userTestDao.findAll(UserTestPredicates.findByForm(req.getData()), PageHelper.of(req));

        return MvcTableResp.<UserTestModel>builder().total(users.getTotalElements())
                .data(users.getContent().stream().map(item -> {
                    UserTestModel userTestModel = new UserTestModel();
                    CopyUtils.objectToObject(item, userTestModel);
                    return userTestModel;
                }).collect(Collectors.toList())).build();

    }

    @Override
    public MvcResult<UserTestModel> create(UserTestModel userTestModel) {

        return apply(item -> {
            UserTest userTest = new UserTest();
            CopyUtils.objectToObject(item, userTest);
            userTestDao.save(userTest);
            return userTestModel;
        }, userTestModel);
    }

    private QUserTest entity = QUserTest.userTest;

    @Transactional
    @Override
    public MvcResult<Boolean> delete(Long id) {

        return apply(item -> {
            UserTest userTest = userTestDao.findOne(entity.id.eq(id).and(entity.delFlag.eq(false)))
                    .orElseThrow(
                            () -> new ServiceException(CodeEnum.ERROR.code(), "用户不存在"));
//            userTestDao.delete(userTest);
            userTest.setDelFlag(true);
            userTestDao.save(userTest);
            return Boolean.TRUE;
        }, id);
    }

    @Transactional
    @Override
    public MvcResult<Boolean> update(UserTestModel userTestModel) {

        return apply(item -> {
            userTestDao.findOne(entity.id.eq(item.getId()).and(entity.delFlag.eq(false)))
                    .orElseThrow(() ->
                            new ServiceException(CodeEnum.ERROR.code(), "用户不存在")
                    );
            UserTest userTest = new UserTest();
            CopyUtils.objectToObject(userTestModel, userTest);
            userTestDao.save(userTest);
            return Boolean.TRUE;
        }, userTestModel);
    }

    @Override
    public MvcResult<UserTestModel> detail(Long id) {
        return apply(item -> {
                    UserTest user = userTestDao.findOne(entity.id.eq(id).and(entity.delFlag.eq(false)))
                            .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "用户不存在"));
                    UserTestModel userTest = new UserTestModel();
                    CopyUtils.objectToObject(user, userTest);
                    return userTest;
                }, id
        );
    }
}
