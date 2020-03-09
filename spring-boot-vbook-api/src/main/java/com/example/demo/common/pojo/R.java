package com.example.demo.common.pojo;

/**
 * Created by ytl on 2019/10/10.
 * <p>
 * 返回结果封装
 */
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.util.UUID;
import lombok.Data;

@Data
public class R<T> {

    private int code;

    private String msg;

    private T data;

    private String errorId;

    private String timestamp;

    private R() {
        this.code = 200;
        this.msg = "success";
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.msg = msg;
        return r;
    }

    public static <T> R ok(T data) {
        R r = new R();
        r.data = data;
        return r;
    }

    public static <T> R ok(T data, String timestamp) {
        R r = new R();
        r.data = data;
        r.timestamp = timestamp;
        return r;
    }

    public static R error(ServiceException e, String uuid) {
        return error(e.getCode(), e.getMessage(), uuid);
    }

    public static R error(ServiceExceptionEnum e) {
        return error(e.getCode(), e.getMessage(), UUID.getUUID());
    }

    public static R error(ServiceExceptionEnum e, String uuid) {
        return error(e.getCode(), e.getMessage(), uuid);
    }

    public static R error(int code, String msg, String uuid) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setErrorId(uuid);
        return r;
    }

    public static <T> R error(int code, String msg, String uuid, T data) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setErrorId(uuid);
        r.setData(data);
        return r;
    }

}
