package com.nancal.common.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * 雪花算法生成主键的ID
 */
public class IdGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Object id = SnowflakeIdHelper.getFlowIdInstance().nextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(s, obj);
    }
}