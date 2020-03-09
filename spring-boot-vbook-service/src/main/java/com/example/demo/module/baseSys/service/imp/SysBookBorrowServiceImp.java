package com.example.demo.module.baseSys.service.imp;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.form.lendRecordForm;
import com.example.demo.service.baseService.ISysBookBorrowService;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.util.LocalDateTimeToStringUtil;
import com.example.demo.common.util.isEmptyUtil;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;
import com.example.demo.pojo.exPojo.form.topLendBookForm;
import com.example.demo.module.baseSys.mapper.SysBookBorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书记录表 baseService
 */
@org.springframework.stereotype.Service("ISysBookBorrowService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysBookBorrowService.class)
public class SysBookBorrowServiceImp implements ISysBookBorrowService {

    @Autowired
    SysBookBorrowMapper sysBookBorrowMapper;

    //按学号或图书号禁用借书记录(由图书表和用户表Service调用)
    @Override
    @Transactional
    public void forbiddenBookBorrowAsStuOrBookId(int state, int type, String stuOrBookId) throws ServiceException {
        bookBorrowEntity forbiddenBookBorrow = new bookBorrowEntity();
        forbiddenBookBorrow.setState(state);
        //判断是启用还是禁用
        if (state == 0) {
            forbiddenBookBorrow.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        }
        //判断是学号还是图书号还是图书名
        UpdateWrapper<bookBorrowEntity> fBBSW = new UpdateWrapper<>();
        if (type == Constant.IS_STU_ID) {
            fBBSW.eq("stu_id", stuOrBookId);
        }
        if (type == Constant.IS_BOOK_ID) {
            fBBSW.eq("book_id", stuOrBookId);
        }
        if (type == Constant.IS_BOOK_NAME) {
            fBBSW.eq("book_name", stuOrBookId);
        }
        sysBookBorrowMapper.update(forbiddenBookBorrow, fBBSW);
    }

    //删除借书记录(由图书表和用户表Service调用)
    @Override
    @Transactional
    public void deleteBookBorrow() throws ServiceException {
        QueryWrapper<bookBorrowEntity> dBBW = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        dBBW.le("forbidden_time", now.plusDays(-Constant.DELETE_FORBIDDEN_RECORD_DAY))
                .eq("state", 0);
        sysBookBorrowMapper.delete(dBBW);
    }

    //批量启用禁用的借书记录
    @Override
    @Transactional
    public void batchEnable(List<userEntity> enableUserList) {
        if (isEmptyUtil.isNotEmpty(enableUserList)) {
            sysBookBorrowMapper.updateBatchEnable(enableUserList);
        }
    }

    //统计某专业，某预计毕业年份借书次数最多的书籍（TOP n）
    @Override
    public IPage<topLendBookDto> topLendBook(topLendBookForm form) throws ServiceException {
        Page<topLendBookDto> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(sysBookBorrowMapper.topLendBookList(page, form));
        return page;
    }

    // 某学号或某图书号 的借书记录
    @Override
    public IPage<bookBorrowEntity> LendRecordListService(lendRecordForm form) throws ServiceException {
        Page<bookBorrowEntity> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(sysBookBorrowMapper.LendRecordList(page, form));
        return page;
    }

}
