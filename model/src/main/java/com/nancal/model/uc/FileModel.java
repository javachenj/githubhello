package com.nancal.model.uc;

import com.nancal.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class FileModel extends BaseModel {
   @ApiModelProperty(name = "文件唯一id")
    private String uuid;
   @ApiModelProperty(name = "文件名称")
    private String fileName;
   @ApiModelProperty(name = "文件名称")
    private Boolean fileStatus;
   @ApiModelProperty(name = "文件后缀")
    private String fileSuffix;
}
