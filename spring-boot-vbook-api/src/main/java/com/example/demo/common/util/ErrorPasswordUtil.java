package com.example.demo.common.util;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ytl on 2019/10/10.
 * <p>
 * 密码输入次数限制 工具类
 */
@Component
public class ErrorPasswordUtil {

    @Autowired
    Session session;
    private static final String ONE_TIMES = "1";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //登录前判断是否密码错误次数超过5次并等待时间未超过 配置等待时间
    public void beforeLogin() {
        if (session.getErrorPasswordTimes() != null) {
            if (Integer.parseInt(session.getErrorPasswordTimes()) % Constant.ERROR_TIMES == 0) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime ldt = LocalDateTime.parse(session.getErrorPasswordTime(), df);
                if (ldt.getDayOfMonth() == now.getDayOfMonth() && ldt.getHour() == now.getHour() && now.getMinute() - ldt.getMinute() < Constant.WAIT_TIME) {
                    throw new ServiceException(ServiceExceptionEnum.LOGIN_ERROR);
                }
            }
        }
    }

    //密码输入错误记录时间并增加密码错误次数
    public void beforeErrorPassword() {
        if (session.getErrorPasswordTimes() != null) {
            session.setErrorPasswordTimes(String.valueOf(Integer.parseInt(session.getErrorPasswordTimes()) + 1));
        } else {
            session.setErrorPasswordTimes(ONE_TIMES);
        }
        LocalDateTime now = LocalDateTime.now();
        session.setErrorPasswordTime(df.format(now));
        throw new ServiceException(ServiceExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
    }

}
