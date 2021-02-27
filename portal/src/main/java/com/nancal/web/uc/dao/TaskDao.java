package com.nancal.web.uc.dao;

import com.nancal.entity.uc.DemoData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<DemoData,Long>, QuerydslPredicateExecutor<DemoData> {

    @Query(value = "select * from demo_data t where t.ORDER_NO=?1")
    List<DemoData> findByOrderNo(String orderNo);

//    @Modifying
//    @Query(value = "update demo_data tank set tank.ORDER_NO= :#{#demoData.orderNo},tank.SORTIE= :#{#demoData.sortie}," +
//            "tank.XBOM_NUMBER= :#{#demoData.xbomNumber},tank.PRODUCT_NAME= :#{#demoData.productName},tank.PLAN_QTY= :#{#demoData.planQty},tank.BATCH= :#{#demoData.batch}," +
//            "tank.CUSTOMER= :#{#demoData.customer},tank.START_DATE= :#{#demoData.startDate},tank.END_DATE= :#{#demoData.endDate},tank.PRODUCT= :#{#demoData.product}" +
//            " where tank.MES_TASK_ID= :mesTaskId")
//    public int updateDemoData( @Param("demoData") DemoData demoData,@Param("mesTaskId") Long mesTaskId);

}
