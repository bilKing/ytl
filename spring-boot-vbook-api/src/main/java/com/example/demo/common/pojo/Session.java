package com.example.demo.common.pojo;

import com.example.demo.common.RedisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ytl on 2019/10/9.
 * <p>
 * session 类
 */
@Component
public class Session {
    @Autowired
    private RedisService redisService;

    //获取userName
    public void setUserName(String userName) {
        redisService.set("userName", userName);
    }

    public String getUserName() {
        return redisService.get("userName");
    }

    public boolean removeUserName() {
        return redisService.remove("userName");
    }

    //获取id
    public void setId(String id) {
        redisService.set("id", id);
    }

    public String getId() {
        return redisService.get("id");
    }

    //获取权限
    public void setUserAuthority(String userAuthority) {
        redisService.set("userAuthority", userAuthority);
    }

    public String getUserAuthority() {
        return redisService.get("userAuthority");
    }

    public boolean removeUserAuthority() {
        return redisService.remove("userAuthority");
    }

    //获取stuId
    public void setStuId(String stuId) {
        redisService.set("stuId", stuId);
    }

    public String getStuId() {
        return redisService.get("stuId");
    }

    public boolean removeStuId() {
        return redisService.remove("stuId");
    }

    //获取输入密码错误次数
    public void setErrorPasswordTimes(String errorPasswordTimes) {
        redisService.set("errorPasswordTimes", errorPasswordTimes);
    }

    public String getErrorPasswordTimes() {
        return redisService.get("errorPasswordTimes");
    }

    //获取密码错误时间
    public void setErrorPasswordTime(String errorPasswordTime) {
        redisService.set("errorPasswordTime", errorPasswordTime);
    }

    public String getErrorPasswordTime() {
        return redisService.get("errorPasswordTime");
    }

}

