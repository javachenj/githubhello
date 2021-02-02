/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.uc.service;

import com.nancal.model.uc.UserModel;
import com.nancal.web.base.MvcResult;
import com.nancal.web.base.MvcTableReq;
import com.nancal.web.base.MvcTableResp;
import com.nancal.web.uc.service.queryform.UserQueryForm;

/**
 * @Description 用户中心模块-用户管理service接口层
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public interface UserService {
    /**
     * 通过条件查询分页数据
     * @param req
     * @return
     */
    public MvcTableResp<UserModel> findPage(MvcTableReq<UserQueryForm> req);

    /**
     * 通过Id查找用户信息
     * @param id
     * @return
     */
    public MvcResult<UserModel> findOne(Long id);

    /**
     * 创建用户信息
     * @param userModel
     * @return
     */
    public MvcResult<UserModel> create(UserModel userModel);

    /**
     * 更新用户信息
     * @param userModel
     * @return
     */
    public MvcResult<UserModel> update(UserModel userModel);

    /**
     * 通过ID删除数据
     * @param id
     * @return
     */
    public MvcResult<Boolean> delete(Long id);

}
