package com.nancal.entity.uc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 126439950L;

    public static final QUser user = new QUser("user");

    public final com.nancal.entity.QBaseEntity _super = new com.nancal.entity.QBaseEntity(this);

    public final StringPath cellphone = createString("cellphone");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final StringPath createUserId = _super.createUserId;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final StringPath email = createString("email");

    public final StringPath familyAddress = createString("familyAddress");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> sex = createNumber("sex", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final StringPath updateUserId = _super.updateUserId;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

