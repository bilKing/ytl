package com.example.demo.common.Interceptor;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.pojo.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ytl on 2019/10/9.
 * <p>
 * 登录认证拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    Session sessionModel;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 视图渲染之后的操作
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 处理请求完成后视图渲染之前的处理操作
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 进入controller层之前拦截请求
        if (StringUtils.isBlank(sessionModel.getStuId())) {
            throw new ServiceException(ServiceExceptionEnum.LOGIN_OVER_TIME);
        }
        return true;
    }

}
