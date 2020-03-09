package com.example.demo.pojo.exPojo.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计各专业借书数量 dto
 *
 */
@Data
public class borrowSpecialitiesCountDto implements Serializable {
    private static final long serialVersionUID = 8140935558525735185L;
    //专业名
    private String userSpecialities;
    //借书本数
    private Integer bookNum;

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
