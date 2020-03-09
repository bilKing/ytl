package com.example.demo.pojo.basePojo.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/10/28.
 * <p>
 * 权限查找 dto
 */
@Data
public class roleDto implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1352997411197940798L;

    /**
     * 权限名
     */
    private String roleName;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
