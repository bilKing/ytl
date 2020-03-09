package com.example.demo.common.AuthToken;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.pojo.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ytl on 2019/10/9.
 * <p>
 * 身份验证
 */
@Aspect
@Component
public class AuthTokenAspect {

    @Pointcut("@annotation(authToken)")
    public void doAuthToken(AuthToken authToken) { }

    @Autowired
    Session session;

    @Around("doAuthToken(authToken)")
    public Object deBefore(ProceedingJoinPoint pjp, AuthToken authToken) throws Throwable {
        int role_name = authToken.role_name();
        int required_Authority = Integer.parseInt(session.getUserAuthority());
        if ((required_Authority & role_name) == role_name) {
            // 身份匹配成功
            return pjp.proceed();
        }
        throw new ServiceException(ServiceExceptionEnum.INSUFFICIENT_PERMISSIONS);
    }

}
