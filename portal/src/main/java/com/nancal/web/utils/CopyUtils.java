/**
 * Nancal.com Inc.
 * Copyright (c) 2021- All Rights Reserved.
 */
package com.nancal.web.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 对象拷贝工具类
 * @date 2021-02-01 13:11:11
 * @author zhangpp
 */
public class CopyUtils {
    /** 创建过的BeanCopier实例放到缓存,下次可以直接获取,提升性能 */
    public static Map<String,BeanCopier> beanCopierMap = new HashMap<>();

    public static void objectToObject(Object source, Object target){
        String beanKey =  generateKey(source.getClass(), target.getClass());

        BeanCopier copier =  null;

        if(!beanCopierMap.containsKey(beanKey)){
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        }else{
            copier = beanCopierMap.get(beanKey);
        }

        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1,Class<?>class2){
        return class1.toString() + class2.toString();
    }
}
