package com.nancal.excel.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DemoDataVo {

    @ApiModelProperty(value = "主键ID")
    private Long mesTaskId;

    @ExcelProperty(value = "任务号",index = 0)
    private String orderNo;

    @ExcelProperty(value = "架次号",index = 1)
    private String sortie;

    @ExcelProperty(value = "XBOM号",index = 2)
    private String xbomNumber;

    @ExcelProperty(value = "产品名称",index = 3)
    private String productName;

    @ExcelProperty(value = "计划数量",index = 4)
    private Integer planQty;

    @ExcelProperty(value = "批次",index = 5)
    private String batch;

    @ExcelProperty(value = "追踪时间",index = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date followTime;

    @ExcelProperty(value = "计划开始时间",index = 7)
    private Date startDate;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ExcelProperty(value = "计划结束时间",index = 8)
    private Date endDate;

    @ExcelProperty(value = "产品图号",index = 9)
    private String product;

    @ExcelProperty(value = "客户名称",index = 10)
    private String customer;

    @ExcelProperty(value = "创建人编码",index = 11)
    private String createByCode;

    @ExcelProperty(value = "创建人名称",index = 12)
    private String createByName;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ExcelProperty(value = "创建时间",index = 13)
    private Date createTime;

    @ExcelProperty(value = "任务状态",index = 14)
    private int mesTaskStatus;
}
