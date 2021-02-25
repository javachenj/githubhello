package com.nancal.entity.uc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDemoData is a Querydsl query type for DemoData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDemoData extends EntityPathBase<DemoData> {

    private static final long serialVersionUID = -496548624L;

    public static final QDemoData demoData = new QDemoData("demoData");

    public final StringPath batch = createString("batch");

    public final StringPath createByCode = createString("createByCode");

    public final StringPath createByName = createString("createByName");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath customer = createString("customer");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> mesTaskId = createNumber("mesTaskId", Long.class);

    public final NumberPath<Integer> mesTaskStatus = createNumber("mesTaskStatus", Integer.class);

    public final StringPath orderNo = createString("orderNo");

    public final NumberPath<Integer> planQty = createNumber("planQty", Integer.class);

    public final StringPath product = createString("product");

    public final StringPath productName = createString("productName");

    public final StringPath sortie = createString("sortie");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath xbomNumber = createString("xbomNumber");

    public QDemoData(String variable) {
        super(DemoData.class, forVariable(variable));
    }

    public QDemoData(Path<? extends DemoData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDemoData(PathMetadata metadata) {
        super(DemoData.class, metadata);
    }

}

