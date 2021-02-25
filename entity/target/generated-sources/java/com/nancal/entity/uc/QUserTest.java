package com.nancal.entity.uc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserTest is a Querydsl query type for UserTest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserTest extends EntityPathBase<UserTest> {

    private static final long serialVersionUID = -1619176512L;

    public static final QUserTest userTest = new QUserTest("userTest");

    public final com.nancal.entity.QBaseEntity _super = new com.nancal.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final StringPath createUserId = _super.createUserId;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final NumberPath<Integer> dept = createNumber("dept", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final StringPath updateUserId = _super.updateUserId;

    public QUserTest(String variable) {
        super(UserTest.class, forVariable(variable));
    }

    public QUserTest(Path<? extends UserTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserTest(PathMetadata metadata) {
        super(UserTest.class, metadata);
    }

}

