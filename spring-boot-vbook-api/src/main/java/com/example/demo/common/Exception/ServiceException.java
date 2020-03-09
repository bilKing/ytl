package com.example.demo.common.Exception;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 业务异常
 */

import com.example.demo.common.Enum.ServiceExceptionEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    //代码
    private int code;
    //消息
    private String message;

    /**
     * dubbo默认是采用Hessian（比jdk自带反序列化高效）进行反序列化的，该反序列化创建对象时，
     * 会取参数最少的构造方法来创建对象，构造方法参数设置默认值，基本类型设置为相应基本类型
     * 的默认值，不是基本类型设置为null
     * <p>
     * 为防止消费者接收 提供者抛出自定义异常 由上述原因导致空指针异常 ，则创建一个无参构造ServiceException
     */
    public ServiceException() { }

    /**
     * 使用枚举构造异常
     * 请尽可能使用这个方法，利于异常枚举的维护
     */
    public ServiceException(ServiceExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    /**
     * 自定义code、msg构造异常
     * 例如：
     * 在校验参数请求的时候，不符合要求的可能有若干个字段，此时返回提示信息组合很多，很难使用枚举
     * 此时，可以根据校验结果动态生成错误提示的msg，在使用此方法抛出异常
     */
    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
