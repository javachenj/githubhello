package com.nancal.entity.uc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "demo_data")
@Data
public class DemoData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MES_TASK_ID")
    private Long mesTaskId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "SORTIE")
    private String sortie;

    @Column(name = "XBOM_NUMBER")
    private String xbomNumber;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PLAN_QTY")
    private Integer planQty;

    @Column(name = "BATCH")
    private String batch;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @Column(name = "START_DATE")
    private Date startDate;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "CUSTOMER")
    private String customer;

    @Column(name = "CREATE_BY_CODE")
    private String createByCode;

    @Column(name = "CREATE_BY_NAME")
    private String createByName;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MES_TASK_STATUS")
    private int mesTaskStatus;
}
