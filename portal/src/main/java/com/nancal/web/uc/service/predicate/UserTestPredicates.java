package com.nancal.web.uc.service.predicate;

import com.nancal.entity.uc.QUserTest;
import com.nancal.web.uc.service.queryform.UserTestQueryForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.StringUtils;


public class UserTestPredicates {
    public static Predicate findByForm(UserTestQueryForm queryForm){
        QUserTest qUserTest = QUserTest.userTest;
        BooleanBuilder builder=new BooleanBuilder();
        if (StringUtils.hasText(String.valueOf(queryForm.getAge()))){
            builder.and(qUserTest.age.eq(queryForm.getAge()));
        }
        if (StringUtils.hasText(queryForm.getName())){
            builder.and(qUserTest.name.like("%"+queryForm.getName()+"%"));
        }
        builder.and(qUserTest.delFlag.eq(false));
        return builder;
    }
}
