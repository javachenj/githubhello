//package com.nancal.minio.base.entity;
//
//import com.nancal.entity.BaseEntity;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//
//import javax.validation.constraints.*;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 部门表 sys_dept
// *
// * @author
// */
//public class SysDept extends BaseEntity {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 部门ID
//     */
//    private Long deptId;
//
//    /**
//     * 父部门ID
//     */
//    private Long parentId;
//
//    /**
//     * 祖级列表
//     */
//    private String ancestors;
//
//    /**
//     * 部门名称
//     */
//    private String deptName;
//
//    /**
//     * 显示顺序
//     */
//    private String orderNum;
//
//    /**
//     * 负责人
//     */
//    private String leader;
//
//    /**
//     * 联系电话
//     */
//    private String phone;
//
//    /**
//     * 邮箱
//     */
//    private String email;
//
//    /**
//     * 部门状态:0正常,1停用
//     */
//    private String status;
//
//    /**
//     * 删除标志（0代表存在 2代表删除）
//     */
//    private String delFlag;
//
//    /**
//     * 父部门名称
//     */
//    private String parentName;
//
//    /**
//     * 子部门
//     */
//    private List<SysDept> children = new ArrayList<SysDept>();
//
//    /**
//     * 维度
//     * */
//    private BigDecimal lat;
//    /**
//     * 经度
//     * */
//    private BigDecimal lon;
//
//    /**
//     * 区域编码
//     * */
//    private String areaCode;
//
//    /**
//     * 制卡网点类型（0：服务网点，1：制卡网点）
//     * */
//    private String outletsType;
//
//    /**
//     * 是否网上办理受理网点
//     * */
//    private String online;
//
//    /**
//     * 详细地址
//     * */
//    private String address;
//
//    /**
//     * 网点代码
//     * */
//    private String brancCode;
//
//    /**
//     * 由谁制卡（制卡网点id）
//     * */
//    private Long makeis;
//
//    /**
//     * 银行类型（2：总行 0：分行 1：网点）
//     * */
//    private String bankType;
//    /**
//     * 是否支持邮寄
//     */
//    private String isByMail;
//
//    public Long getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(Long deptId) {
//        this.deptId = deptId;
//    }
//
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    public String getAncestors() {
//        return ancestors;
//    }
//
//    public void setAncestors(String ancestors) {
//        this.ancestors = ancestors;
//    }
//
//    @NotBlank(message = "部门名称不能为空")
//    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
//    public String getDeptName() {
//        return deptName;
//    }
//
//    public void setDeptName(String deptName) {
//        this.deptName = deptName;
//    }
//
//    @NotBlank(message = "显示顺序不能为空")
//    public String getOrderNum() {
//        return orderNum;
//    }
//
//    public void setOrderNum(String orderNum) {
//        this.orderNum = orderNum;
//    }
//
//    public String getLeader() {
//        return leader;
//    }
//
//    public void setLeader(String leader) {
//        this.leader = leader;
//    }
//
//    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    @Email(message = "邮箱格式不正确")
//    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getDelFlag() {
//        return delFlag;
//    }
//
//    public void setDelFlag(String delFlag) {
//        this.delFlag = delFlag;
//    }
//
//    public String getParentName() {
//        return parentName;
//    }
//
//    public void setParentName(String parentName) {
//        this.parentName = parentName;
//    }
//
//    public List<SysDept> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<SysDept> children) {
//        this.children = children;
//    }
//
//
//    public BigDecimal getLat() {
//        return lat;
//    }
//
//
//    public void setLat(BigDecimal lat) {
//        this.lat = lat;
//    }
//
//
//    public BigDecimal getLon() {
//        return lon;
//    }
//
//
//    public void setLon(BigDecimal lon) {
//        this.lon = lon;
//    }
//
//
//    public String getAreaCode() {
//        return areaCode;
//    }
//
//
//    public void setAreaCode(String areaCode) {
//        this.areaCode = areaCode;
//    }
//
//
//    public Long getMakeis() {
//        return makeis;
//    }
//
//    public void setMakeis(Long makeis) {
//        this.makeis = makeis;
//    }
//
//    public String getOnline() {
//        return online;
//    }
//
//
//    public void setOnline(String online) {
//        this.online = online;
//    }
//
//
//    public String getAddress() {
//        return address;
//    }
//
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//
//
//
//    public String getBrancCode() {
//        return brancCode;
//    }
//
//
//    public void setBrancCode(String brancCode) {
//        this.brancCode = brancCode;
//    }
//
//    public String getOutletsType() {
//        return outletsType;
//    }
//
//
//    public void setOutletsType(String outletsType) {
//        this.outletsType = outletsType;
//    }
//
//    @Max(value=2,message = "bankType值不在允许范围内")
//    @Min(value=0,message = "bankType值不在允许范围内")
//    public String getBankType() {
//        return bankType;
//    }
//
//
//    public void setBankType(String bankType) {
//        this.bankType = bankType;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
//                .append("deptId", getDeptId())
//                .append("parentId", getParentId())
//                .append("ancestors", getAncestors())
//                .append("deptName", getDeptName())
//                .append("orderNum", getOrderNum())
//                .append("leader", getLeader())
//                .append("phone", getPhone())
//                .append("email", getEmail())
//                .append("status", getStatus())
//                .append("delFlag", getDelFlag())
//                .append("createBy", getCreateBy())
//                .append("createTime", getCreateTimea())
//                .append("updateBy", getUpdateBy())
//                .append("updateTime", getUpdateTime())
//                .append("lat", getLat())
//                .append("lon", getLon())
//                .append("areaCode", getAreaCode())
//                .append("makeis", getMakeis())
//                .append("online", getOnline())
//                .append("address", getAddress())
//                .append("branch_code", getBrancCode())
//                .append("outletsType", getOutletsType())
//                .append("bankType", getBankType())
//                .append("isByMail", getIsByMail())
//                .toString();
//    }
//
//    public String getIsByMail() {
//        return isByMail;
//    }
//
//    public SysDept setIsByMail(String isByMail) {
//        this.isByMail = isByMail;
//        return this;
//    }
//}
