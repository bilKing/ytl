package com.example.demo.module.baseSys.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.util.AssertUtil;
import com.example.demo.entity.baseEntity.bookEntity;
import com.example.demo.pojo.basePojo.dto.bookBorrowDto;
import com.example.demo.pojo.basePojo.form.bookBorrowForm;
import com.example.demo.pojo.basePojo.form.bookForm;
import com.example.demo.service.baseService.ISysBookBorrowService;
import com.example.demo.service.baseService.ISysBookService;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.util.BeanUtils;
import com.example.demo.common.util.LocalDateTimeToStringUtil;
import com.example.demo.common.util.UUID;
import com.example.demo.module.baseSys.mapper.SysBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 *
 * 图书表 baseService
 */
@org.springframework.stereotype.Service("ISysBookService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysBookService.class)
public class SysBookServiceImp implements ISysBookService {

    @Autowired
    SysBookMapper sysBookMapper;

    @Autowired
    ISysBookBorrowService IsysBookBorrowService;

    //添加图书
    @Override
    @Transactional
    public void addBookService(bookForm form) {
        bookEntity newBook = new bookEntity();
        //判断是否有相同图书号图书
        QueryWrapper<bookEntity> bIW = new QueryWrapper<>();
        bIW.eq("book_id",form.getBookId());
        AssertUtil.AssertNull(sysBookMapper.selectOne(bIW),"已存在该图书号图书");
        //设置uuid,设置创建年份
        newBook.setId(UUID.getUUID());
        newBook.setState(Constant.ENABLE);
        newBook.setBookAddYear(LocalDateTime.now().getYear());
        BeanUtils.copyProperties(form, newBook);
        sysBookMapper.insert(newBook);
    }

    //批量添加图书
    @Override
    @Transactional
    public void batchAddBookService(List<bookEntity> list) {
        sysBookMapper.insertNewBooks(list);
    }

    //启用,禁用图书
    @Override
    @Transactional
    public void forbiddenBookService(int state, String id, boolean isName) {
        bookEntity forbiddenBook = new bookEntity();
        forbiddenBook.setState(state);
        //禁用时间
        if (state == 0) {
            forbiddenBook.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        }
        UpdateWrapper<bookEntity> fBW = new UpdateWrapper<>();
        if (isName) {
            fBW.eq("book_name", id);
            //禁用借书表
            IsysBookBorrowService.forbiddenBookBorrowAsStuOrBookId(state, Constant.IS_BOOK_NAME, id);
        } else {
            fBW.eq("book_id", id);
            //禁用借书表
            IsysBookBorrowService.forbiddenBookBorrowAsStuOrBookId(state, Constant.IS_BOOK_ID, id);
        }
        sysBookMapper.update(forbiddenBook, fBW);
    }

    //删除图书
    @Override
    @Transactional
    public void deleteBookService() {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<bookEntity> dFUW = new QueryWrapper<>();
        dFUW.le("forbidden_time", now.plusDays(-Constant.DELETE_FORBIDDEN_BOOK_DAY))
                .eq("state", 0);
        sysBookMapper.delete(dFUW);
        //删除借书表信息
        IsysBookBorrowService.deleteBookBorrow();
    }

    //借书返回列表
    @Override
    public IPage<bookBorrowDto> borrowBookListService(bookBorrowForm form) throws ServiceException {
        Page<bookBorrowDto> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(sysBookMapper.selectBookBorrowList(page, form));
        return page;
    }

    //管理员查看书籍信息列表
    @Override
    public IPage<bookEntity> bookListService(bookBorrowForm form) throws ServiceException {
        Page<bookEntity> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(sysBookMapper.selectBookList(page, form));
        return page;
    }

}
