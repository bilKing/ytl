package com.example.demo.common.util;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 参数校验工具类 现选择AsserUtil检验
 */

import com.example.demo.common.Exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {

    // 参数校验错误代码
    private static final int PARAM_ERROR_CODE = 400;

    // validator校验对象
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws ServiceException 校验不通过，则报RException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws ServiceException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append(";");
            }
            throw new ServiceException(PARAM_ERROR_CODE, StringUtils.chop(msg.toString()));
        }
    }

}