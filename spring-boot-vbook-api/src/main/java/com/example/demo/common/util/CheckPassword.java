package com.example.demo.common.util;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;

/**
 * Created by ytl on 2019/10/10.
 * <p>
 * 检验密码正则 工具类
 */
public class CheckPassword {
    //检查注册密码是否以下划线字母数字开头并长度在6到12之间
    public static void checkPassword(String password) throws ServiceException {
        String regex = "(?=^[a-zA-Z0-9_])^.{6,12}$";
        if (!password.matches(regex)) {
            throw new ServiceException(ServiceExceptionEnum.PASSWORD_FALSE);
        }
    }
}
