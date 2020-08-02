package com.chang.ccloud.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author changxizhao
 * @Date 2020/8/1 17:13
 * @Description
 */
public class MapConvertUtil {

    /**
     *
     * @param: bean
     * @return java.util.Map
     * @throws
     * @Author changxizhao
     * @Date 2020/8/1 17:23
     */
    public static Map beanToMap(Object bean) {
        try {
            Class type = bean.getClass();
            Map returnMap = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    }
                }
            }
            return returnMap;
        }catch (Exception e) {
            return new HashMap<>();
        }
    }
}
