package com.example.demo.module.exSys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.pojo.basePojo.form.userBorrowForm;
import com.example.demo.service.baseService.ISysBookBorrowService;
import com.example.demo.service.baseService.ISysBookLendService;
import com.example.demo.service.baseService.ISysVbookService;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import com.example.demo.common.util.AssertUtil;
import com.example.demo.common.util.isEmptyUtil;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;
import com.example.demo.pojo.exPojo.form.borrowOutTimeForm;
import com.example.demo.pojo.exPojo.form.borrowSpecialitiesCountForm;
import com.example.demo.pojo.exPojo.form.topLendBookForm;
import com.example.demo.module.exSys.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by ytl on 2019/10/15.
 * <p>
 * 统计表 控制器
 */
@RequestMapping(value = "/Statistics")
@RestController
public class ExStatisticsController {

    @Reference(loadbalance = "roundrobin")
    ISysVbookService userService;

    @Reference(loadbalance = "roundrobin")
    ISysBookBorrowService borrowService;

    @Autowired
    IExportService IexportService;

    @Reference(loadbalance = "roundrobin")
    ISysBookLendService IsysBookLendService;

    //统计各专业，某预计毕业年份，某时间段内借书本数（借书时间大于 n天）
    @PostMapping(value = "/borrowCount")
    @AuthToken(role_name = Constant.statistics)
    public R<Page<List<borrowSpecialitiesCountDto>>> borrowSpecialitiesCount(@RequestBody borrowSpecialitiesCountForm form) {
        AssertUtil.AssertNotAFutureDate(AssertUtil.StringToLocalDate(form.getEnd()), AssertUtil.StringToLocalDate(form.getStart()));
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        return R.ok(userService.borrowCountService(form));
    }

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）
    @PostMapping(value = "/userBorrowCount")
    @AuthToken(role_name = Constant.statistics)
    public R<Page<List<userBorrowDto>>> userBorrowCount(@RequestBody userBorrowForm form) {
        AssertUtil.AssertNotAFutureDate(AssertUtil.StringToLocalDate(form.getEnd()), AssertUtil.StringToLocalDate(form.getStart()));
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        return R.ok(userService.userBorrowCountService(form));
    }

    //统计超过n天借书未还的 书本和学号和借书时间
    @PostMapping(value = "/borrowOutTime")
    @AuthToken(role_name = Constant.statistics)
    public R<Page<List<borrowOutTimeDto>>> borrowOutTime(@RequestBody borrowOutTimeForm outTimeForm) {
        AssertUtil.AssertNotNull(outTimeForm.getDay(), "未还天数不能为空");
        return R.ok(IsysBookLendService.borrowOutTimeService(outTimeForm));
    }

    //统计某专业，某预计毕业年份借书次数最多的书籍名字和数量
    @PostMapping(value = "/topLendBook")
    @AuthToken(role_name = Constant.statistics)
    public R<Page<List<topLendBookDto>>> topLendBook(@RequestBody topLendBookForm form) {
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        return R.ok(borrowService.topLendBook(form));
    }

    //======================== 统计导出接口 ========================

    //统计各专业，某预计毕业年份，某时间段内借书本数（借书时间大于 n天）-----导出
    @PostMapping(value = "/borrowCountExportDownload")
    @AuthToken(role_name = Constant.statistics)
    public void borrowCountExportDownload(HttpServletResponse resp, @RequestBody borrowSpecialitiesCountForm form) throws IOException {
        AssertUtil.AssertNotAFutureDate(AssertUtil.StringToLocalDate(form.getEnd()), AssertUtil.StringToLocalDate(form.getStart()));
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        IexportService.borrowCountExportService(userService.borrowCountService(form));
        //将文件变成流发送前端
        IexportService.streamService(resp, Constant.BORROW_COUNT, true);
    }

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）-----导出
    @PostMapping(value = "/userBorrowCountExportDownload")
    @AuthToken(role_name = Constant.statistics)
    public void userBorrowCountExportDownload(HttpServletResponse resp, @RequestBody userBorrowForm form) throws IOException {
        AssertUtil.AssertNotAFutureDate(AssertUtil.StringToLocalDate(form.getEnd()), AssertUtil.StringToLocalDate(form.getStart()));
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        IexportService.userBorrowCountExportService(userService.userBorrowCountService(form));
        //将文件变成流发送前端
        IexportService.streamService(resp, Constant.USER_BORROW_COUNT, true);
    }

    //统计超过n天借书未还的 书本和学号和借书时间 -----导出
    @PostMapping(value = "/borrowOutTimeExportDownload")
    @AuthToken(role_name = Constant.statistics)
    public void borrowOutTimeExportDownload(HttpServletResponse resp, @RequestBody borrowOutTimeForm form) throws IOException {
        AssertUtil.AssertNotNull(form.getDay(), "未还天数不能为空");
        IexportService.borrowOutTimeExportService(IsysBookLendService.borrowOutTimeService(form));
        //将文件变成流发送前端
        IexportService.streamService(resp, Constant.BORROW_OUT_TIME_EXPORT, true);
    }

    //统计某专业，某预计毕业年份借书次数最多的书籍名字和数量 -----导出
    @PostMapping(value = "/topLendBookExportDownload")
    @AuthToken(role_name = Constant.statistics)
    public void topLendBookExportDownload(HttpServletResponse resp, @RequestBody topLendBookForm form) throws IOException {
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        IexportService.topLendBookExportService(borrowService.topLendBook(form));
        //将文件变成流发送前端
        IexportService.streamService(resp, Constant.TOP_LEND_BOOK, true);
    }

}
