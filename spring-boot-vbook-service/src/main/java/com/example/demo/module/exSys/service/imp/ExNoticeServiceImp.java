package com.example.demo.module.exSys.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.Session;
import com.example.demo.common.util.LocalDateTimeToStringUtil;
import com.example.demo.common.util.UUID;
import com.example.demo.entity.exEntity.noticeEntity;
import com.example.demo.pojo.exPojo.dto.noticeListDto;
import com.example.demo.service.baseService.ISysVbookService;
import com.example.demo.service.exService.IExNoticeService;
import com.example.demo.module.exSys.mapper.ExNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ytl on 2019/10/24.
 * <p>
 * 公告Service实现类
 */
@org.springframework.stereotype.Service("IExNoticeService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IExNoticeService.class)
public class ExNoticeServiceImp implements IExNoticeService {

    @Autowired
    Session session;

    @Autowired
    ExNoticeMapper exNoticeMapper;

    //公告返回列表
    @Override
    public IPage<noticeListDto> noticeListService(int current, int size) throws ServiceException {
        Page<noticeListDto> page = new Page();
        page.setSize(size);
        page.setCurrent(current);
        return exNoticeMapper.selectNoticeList(page);
    }

    //新增公告
    @Override
    @Transactional
    public void noticeAddService(String notice) throws ServiceException {
        noticeEntity newNotice = new noticeEntity();
        newNotice.setAdminName(session.getUserName());
        newNotice.setId(UUID.getUUID());
        newNotice.setNoticeTime(LocalDateTimeToStringUtil.formatterDay());
        newNotice.setNotice(notice);
        exNoticeMapper.insert(newNotice);
    }

    //删除公告
    @Override
    @Transactional
    public void noticeDeleteService(String id) throws ServiceException {
        QueryWrapper<noticeEntity> dNQW = new QueryWrapper<>();
        dNQW.eq("id", id);
        exNoticeMapper.delete(dNQW);
    }

}
