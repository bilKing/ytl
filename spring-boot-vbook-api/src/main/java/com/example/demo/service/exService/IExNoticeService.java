package com.example.demo.service.exService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.pojo.exPojo.dto.noticeListDto;

/**
 * Created by Administrator on 2019/10/24.
 * <p>
 * 公告Service
 */
public interface IExNoticeService {

    //公告返回列表
    IPage<noticeListDto> noticeListService(int current, int size) throws ServiceException;

    //新增公告
    void noticeAddService(String notice) throws ServiceException;

    //删除公告
    void noticeDeleteService(String id) throws ServiceException;

}
