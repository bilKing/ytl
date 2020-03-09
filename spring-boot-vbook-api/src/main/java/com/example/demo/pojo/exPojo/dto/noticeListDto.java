package com.example.demo.pojo.exPojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/10/24.
 *
 * 公告返回列表
 *
 */
@Data
public class noticeListDto implements Serializable {
    private static final long serialVersionUID = 1816263449429535913L;
    //主键id
    private String id;
    //公告内容
    private String notice;
    //公告时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String noticeTime;
    //公示人
    private String adminName;
}
