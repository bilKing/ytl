package com.example.demo.module.exSys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import com.example.demo.pojo.exPojo.dto.noticeListDto;
import com.example.demo.pojo.exPojo.form.noticeForm;
import com.example.demo.service.exService.IExNoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/10/24.
 * <p>
 * 公告 控制器
 */
//======================== 拓展公告接口 ========================

@RequestMapping(value = "/notice")
@RestController
public class ExNoticeController {

    @Reference(loadbalance = "roundrobin")
    IExNoticeService IexNoticeService;

    //公告返回列表
    @PostMapping(value = "/noticeList")
    public R<Page<List<noticeListDto>>> noticeList(@RequestBody HashMap<String, String> map) {
        int size = Integer.parseInt(map.get("size"));
        int current = Integer.parseInt(map.get("current"));
        return R.ok(IexNoticeService.noticeListService(current, size));
    }

    //新增公告
    @PostMapping(value = "/noticeAdd")
    @AuthToken(role_name = Constant.addAnnouncement)
    public R noticeAdd(@RequestBody noticeForm form) {
        IexNoticeService.noticeAddService(form.getNotice());
        return R.ok();
    }

    //删除公告
    @PostMapping(value = "/noticeDelete")
    @AuthToken(role_name = Constant.addAnnouncement)
    public R noticeDelete(@RequestBody HashMap<String, String> map) {
        String id = map.get("id");
        IexNoticeService.noticeDeleteService(id);
        return R.ok();
    }

}
