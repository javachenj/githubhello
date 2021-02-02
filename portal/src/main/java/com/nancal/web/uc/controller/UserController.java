/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.uc.controller;

import com.nancal.model.uc.UserModel;
import com.nancal.web.base.MvcResult;
import com.nancal.web.base.MvcTableReq;
import com.nancal.web.base.MvcTableResp;
import com.nancal.web.uc.service.UserService;
import com.nancal.web.uc.service.queryform.UserQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户中心模块-用户管理控制器
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Slf4j
@RestController
@RequestMapping("/uc/user")
@Api(tags = "用户中心-用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="分页获取用户列表", notes="")
    @PostMapping(value = "/list")
    public MvcTableResp<UserModel> list(
            @RequestBody MvcTableReq<UserQueryForm> req) {
        return userService.findPage(req);
    }

    @ApiOperation(value="获取用户详情", notes="")
    @PostMapping(value = "/detail")
    public MvcResult<UserModel> detail(@RequestParam("id") Long id) {
        return userService.findOne(id);
    }

    @ApiOperation(value="创建用户", notes="")
    @PostMapping(value = "/create")
    public MvcResult<UserModel> create(@RequestBody UserModel user) {
        return userService.create(user);
    }

    @ApiOperation(value="修改用户基本信息", notes="")
    @PostMapping(value = "/update")
    public MvcResult<UserModel> update(@RequestBody UserModel user) {
        return userService.update(user);
    }

    @ApiOperation(value="删除用户", notes="")
    @PostMapping(value = "/delete")
    public MvcResult<Boolean> delete(
            @ApiParam(required = false, name = "id", value = "用户ID", example = "0")
            @RequestParam(value = "id", defaultValue = "0") Long id) {
        return userService.delete(id);
    }
}
