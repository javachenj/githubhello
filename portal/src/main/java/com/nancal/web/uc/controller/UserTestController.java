package com.nancal.web.uc.controller;

import com.nancal.model.uc.UserTestModel;
import com.nancal.web.base.MvcResult;
import com.nancal.web.base.MvcTableReq;
import com.nancal.web.base.MvcTableResp;
import com.nancal.web.uc.service.UserTestService;
import com.nancal.web.uc.service.queryform.UserTestQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/userTest")
@RestController
@Api(tags = "用户测试中心")
public class UserTestController {
    @Autowired
    private UserTestService userTestService;
    @ApiOperation("分页获取用户列表")
    @PostMapping("/page")
    public MvcTableResp<UserTestModel> page(@RequestBody MvcTableReq<UserTestQueryForm> req){
        return userTestService.page(req);
    }
    @ApiOperation("新增用户")
    @PostMapping("/create")
    public MvcResult<UserTestModel> create(@RequestBody UserTestModel userTestModel){
        return userTestService.create(userTestModel);
    }
    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public MvcResult<Boolean> delete(@ApiParam(required = false,name = "id",value = "用户id",example = "0")
        @RequestParam(value = "id",defaultValue = "0") Long id){
        return userTestService.delete(id);
    }
    @ApiOperation("修改用户")
    @PostMapping("/update")
    public MvcResult<Boolean> update(@RequestBody UserTestModel userTestModel){
        return userTestService.update(userTestModel);
    }
    @ApiOperation("查询用户详情")
    @PostMapping("/detail")
    public MvcResult<UserTestModel> detail(@RequestParam Long id){
        return userTestService.detail(id);
    }
}
