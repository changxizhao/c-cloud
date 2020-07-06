package com.chang.ccloud.common.utils;

import java.lang.reflect.Field;

/**
 *
 * @Author changxizhao
 * @Date 2020/7/5 19:13
 * @Description
 */
public class BaseObjectConverterUtil {

    /**
     * 具有相同属性的不同类相互转化，适用于基础类型的属性，复杂属性不处理
     * @param: source 原对象
     * @param: target 目标类型
     * @return T
     * @throws
     * @Author changxizhao
     * @Date 2020/7/5 20:31
     */
    public static <T> T copyProperties(Object source,Class<?> target) throws IllegalAccessException, InstantiationException {

        Object result = target.newInstance();
        Class clazz = source.getClass();
        // 获取对象属性
        Field[] sourceFields = clazz.getDeclaredFields();
        Field[] targetFields = target.getDeclaredFields();

        for (Field targetField: targetFields) {
            for(Field sourceField: sourceFields){
                String sourceFieldName = sourceField.getName();
                sourceField.setAccessible(true); // 私有属性必须设置访问权限
                Object sourceFieldValue = sourceField.get(source);
                if(targetField.getName().equals(sourceFieldName)) {
                    targetField.setAccessible(true);
                    targetField.set(result, sourceFieldValue);
                }
            }
        }
        return (T) result;
    }

}