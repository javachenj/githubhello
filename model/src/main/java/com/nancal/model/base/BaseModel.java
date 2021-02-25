package com.nancal.model.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel(value = "基础类")
@Data
@ToString
@EqualsAndHashCode
public class BaseModel {

    @ApiModelProperty(value = "ID", example = "0")
    private Long id = 0L;

    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createAt;

    @ApiModelProperty(value = "更新时间")
    protected LocalDateTime updateAt;

    @ApiModelProperty(value = "创建者ID")
    protected String createUserId;

    @ApiModelProperty(value = "更新者ID")
    protected String updateUserId;

    @ApiModelProperty(value = "创建人")
    protected String createBy;

    @ApiModelProperty(value = "更新人")
    protected String updateBy;

    @ApiModelProperty(name = "是否删除")
    protected Boolean delFlag;
}
