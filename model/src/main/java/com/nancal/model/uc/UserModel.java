/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.model.uc;

import com.nancal.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description 用户Model定义
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseModel {

    @ApiModelProperty(name = "用户名称")
    private String name;

    @ApiModelProperty(name = "用户性别")
    private Integer sex;

    @ApiModelProperty(name = "手机号")
    private String cellphone;

    @ApiModelProperty(name = "邮箱号")
    private String email;

    @ApiModelProperty(name = "家庭住址")
    private String familyAddress;


}
