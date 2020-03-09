package com.example.demo.module.baseSys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.util.AssertUtil;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.pojo.basePojo.form.lendRecordForm;
import com.example.demo.service.baseService.ISysBookBorrowService;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书表记录 控制器
 */
@RequestMapping(value = "/Borrow")
@RestController
public class SysBookBorrowController {

    @Reference(loadbalance = "roundrobin")
    ISysBookBorrowService service;

    // 某学号或某图书号 的借书记录
    @PostMapping(value = "/LendRecordList")
    @AuthToken(role_name = Constant.statistics)
    public R<Page<List<bookBorrowEntity>>> LendRecordList(@RequestBody lendRecordForm form) {
        return R.ok(service.LendRecordListService(form));
    }

}
