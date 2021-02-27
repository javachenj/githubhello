package com.nancal.entity.uc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = 125983679L;

    public static final QFile file = new QFile("file");

    public final com.nancal.entity.QBaseEntity _super = new com.nancal.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final StringPath createUserId = _super.createUserId;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final StringPath fileName = createString("fileName");

    public final BooleanPath fileStatus = createBoolean("fileStatus");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final StringPath updateUserId = _super.updateUserId;

    public final StringPath uuid = createString("uuid");

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}

