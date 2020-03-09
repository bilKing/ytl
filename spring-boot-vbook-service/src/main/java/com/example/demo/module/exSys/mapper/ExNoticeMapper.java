package com.example.demo.module.exSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.pojo.Page;
import com.example.demo.entity.exEntity.noticeEntity;
import com.example.demo.pojo.exPojo.dto.noticeListDto;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/10/24.
 * <p>
 * 公告mapper
 */
public interface ExNoticeMapper extends BaseMapper<noticeEntity> {

    //公告返回列表
    IPage<noticeListDto> selectNoticeList(@Param("page") Page<noticeListDto> page);

}
