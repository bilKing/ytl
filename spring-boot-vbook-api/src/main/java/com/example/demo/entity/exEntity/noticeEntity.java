package com.example.demo.entity.exEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by ytl on 2019/10/24.
 * <p>
 * 公告控制器
 */
@Data
@TableName("ex_notice")
public class noticeEntity {

    /**
     * 主键id
     */
    private String id;

    /**
     * 公告内容
     */
    private String notice;

    /**
     * 公告时间
     */
    private String noticeTime;

    /**
     * 公示人
     */
    private String adminName;

}
