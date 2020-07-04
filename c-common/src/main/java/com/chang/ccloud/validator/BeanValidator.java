package com.chang.ccloud.validator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.chang.ccloud.exception.ParamsException;
import org.apache.commons.collections4.MapUtils;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * 参数校验实体类
 * @Author changxizhao
 * @Date 2020/7/4 15:19
 * @Description
 */
public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if(validateResult.isEmpty()) {
            return Collections.emptyMap();
        }else {
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation)iterator.next();
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do {
            if(!iterator.hasNext()) {
                return Collections.emptyMap();
            }

            Object object = iterator.next();
            errors = validate(object,new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if(objects != null && objects.length > 0){
            return validateList(Lists.asList(first, objects));
        }else {
            return validate(first,new Class[0]);
        }
    }

    /**
     * 校验参数，如果校验不通过，抛出参数校验异常
     * @param: object
     * @return void
     * @throws ParamsException
     * @Author changxizhao
     * @Date 2020/7/4 20:01
     */
    public static void checkObject(Object object) throws ParamsException {
        Map<String, String> map = validateObject(object);
        if(MapUtils.isNotEmpty(map)) {
            throw new ParamsException(map.toString());
        }
    }
}
