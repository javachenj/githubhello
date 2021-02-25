package com.nancal.model.uc;

import com.nancal.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserTestModel extends BaseModel {
   @ApiModelProperty(name = "用户名称")
    private String name;
   @ApiModelProperty(name = "年龄")
    private Integer age;
   @ApiModelProperty(name = "部门")
    private Integer dept;
}
