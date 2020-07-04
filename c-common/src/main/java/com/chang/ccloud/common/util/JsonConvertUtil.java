package com.chang.ccloud.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * json转换工具类
 * @Author changxizhao
 * @Date 2020/7/4 17:24
 * @Description
 */
public class JsonConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonConvertUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    public static <T> String obj2String(T o) {
        if(o == null) {
            return null;
        }
        try {
            return o instanceof String ? (String)o : objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            logger.warn("parse object to String exception : {}",e.getMessage());
            return null;
        }
    }

    public static <T> T String2Obj(String s, TypeReference<T> typeReference) {
        if(s == null || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? s : objectMapper.readValue(s, typeReference));
        } catch (IOException e) {
            logger.warn("parse string to Object exception : {}",e.getMessage());
            return null;
        }
    }
}
