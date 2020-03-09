package com.example.demo.module.baseSys.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.bookLendEntity;
import com.example.demo.pojo.basePojo.dto.bookReturnDto;
import com.example.demo.pojo.basePojo.form.bookReturnForm;
import com.example.demo.service.baseService.ISysBookLendService;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.Session;
import com.example.demo.common.util.*;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.form.borrowOutTimeForm;
import com.example.demo.module.baseSys.mapper.SysBookLendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytl on 2019/10/17.
 * <p>
 * 借书表 baseService
 */
@org.springframework.stereotype.Service("ISysBookLendService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysBookLendService.class)
public class SysBookLendServiceImp implements ISysBookLendService {

    @Autowired
    Session session;

    @Autowired
    SysBookLendMapper sysBookLendMapper;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor executor;

    private static final int NOT_LEND = 0;

    //借书
    @Override
    @Transactional
    public void borrowBookService(String bookId) throws ServiceException {
        //重复借书,或者书籍不存在
        if (sysBookLendMapper.selectLend(bookId) == 0) {
            throw new ServiceException(ServiceExceptionEnum.BOOK_ID_Lend);
        }
        //建立新借书信息
        bookLendEntity newBorrow = new bookLendEntity();
        //搜索图书名
        String bookName = sysBookLendMapper.selectBookName(bookId);
        if (isEmptyUtil.isNotEmpty(bookName)) {
            newBorrow.setBookName(bookName);
        }
        //插入借书表
        newBorrow.setBookId(bookId);
        newBorrow.setStuId(session.getStuId());
        newBorrow.setId(UUID.getUUID());
        newBorrow.setBorrowTime(LocalDateTimeToStringUtil.formatter());
        sysBookLendMapper.insert(newBorrow);
        //在图书表中更新信息
        sysBookLendMapper.updateBorrowBook(newBorrow);
    }

    //还书
    @Override
    @Transactional
    public int returnBookService(String id) throws ServiceException {
        //查找出借时间和计算借书时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<bookLendEntity> bBTW = new QueryWrapper<>();
        bBTW.select("borrow_time").eq("id", id);
        bookLendEntity returnBookInf = sysBookLendMapper.selectOne(bBTW);
        //判断是否有该书籍信息
        AssertUtil.AssertNotNull(returnBookInf, "没有此书籍借书信息");
        LocalDateTime borrowTime = LocalDateTime.parse(sysBookLendMapper.selectOne(bBTW).getBorrowTime(), formatter);
        //设置更新数据
        bookLendEntity returnBook = new bookLendEntity();
        returnBook.setReturnTime(now.format(formatter));
        returnBook.setLend(NOT_LEND);
        returnBook.setLendDay(PassDayUtil.passYearUtil(borrowTime, now));
        //借书表中更新信息
        executor.execute(() -> {
            UpdateWrapper<bookLendEntity> rBW = new UpdateWrapper<>();
            rBW.eq("id", id);
            sysBookLendMapper.update(returnBook, rBW);
        });
        //图书表更新
        QueryWrapper<bookLendEntity> sBIW = new QueryWrapper<>();
        sBIW.select("book_id").eq("id", id);
        returnBook.setBookId(sysBookLendMapper.selectOne(sBIW).getBookId());
        sysBookLendMapper.updateReturnBook(returnBook);
        return PassDayUtil.passYearUtil(borrowTime, now);
    }

    //还书列表
    @Override
    public IPage<bookReturnDto> returnBookListService(String stuId, bookReturnForm form) throws ServiceException {
        Page<bookReturnDto> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(sysBookLendMapper.selectBookReturnList(page, stuId));
        return page;
    }

    //借书表中已还的书记录删除 并添加到借书记录路表中
    //@Async 异步需要再加一层 调用两个线程 才能实现事务回滚，否则事务回滚不生效 （暂未开启异步）
    @Override
    @Transactional
    public void addToBorrowService() throws ServiceException {
        //搜索借书表中已还的书籍
        List<bookBorrowEntity> alreadyRepaidList = sysBookLendMapper.alreadyRepaidList();
        //将借书记录添加到 借书记录表
        //批量分页插入
        int size = Constant.BATCH_INSERT;
        long totalPage = 1;
        int start = 0;
        int end = 0;
        //计算总页数
        if (alreadyRepaidList.size() % size == 0) {
            totalPage = alreadyRepaidList.size() / size;
        } else {
            totalPage = alreadyRepaidList.size() / size + 1;
        }
        for (long current = 1; current <= totalPage; current++) {
            //插入
            if (start + size > alreadyRepaidList.size()) {
                if (start - size < 0) {
                    sysBookLendMapper.insertAlreadyRepaidList(alreadyRepaidList);
                    break;
                }
                end = alreadyRepaidList.size();
            }
            if (start + size <= alreadyRepaidList.size()) {
                end = start + size;
            }
            List<bookBorrowEntity> list = alreadyRepaidList.subList(start, end);
            sysBookLendMapper.insertAlreadyRepaidList(list);
            start = start + size;
        }
        //删除借书表中已还的书籍
        executor.submit(() -> {
            QueryWrapper<bookLendEntity> deleteReturn = new QueryWrapper<>();
            deleteReturn.eq("lend", 0);
            sysBookLendMapper.delete(deleteReturn);
        });
    }

    //统计超过n天借书未还的书本 和 学号
    @Override
    public IPage<borrowOutTimeDto> borrowOutTimeService(borrowOutTimeForm outTimeForm) throws ServiceException {
        //分页搜索未还书用户的学号 图书号 借书时间
        Page<bookBorrowEntity> page = new Page<>();
        page.setSize(outTimeForm.getSize());
        page.setCurrent(outTimeForm.getCurrent());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String targetTime = now.plusDays(-outTimeForm.getDay()).format(formatt);
        List<bookBorrowEntity> outTimeEntityList = sysBookLendMapper.selectOutTimeBorrowList(outTimeForm, targetTime, page);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<borrowOutTimeDto> outTimeDtoList = new ArrayList<>();
        //将未还书的用户 借书时间超过n天的加入List<dto>
        Iterator<bookBorrowEntity> iter = outTimeEntityList.iterator();
        while (iter.hasNext()) {
            bookBorrowEntity outTimeEntity = iter.next();
            borrowOutTimeDto bOD = new borrowOutTimeDto();
            bOD.setStuId(outTimeEntity.getStuId());
            bOD.setBookId(outTimeEntity.getBookId());
            bOD.setBorrowDay(PassDayUtil.passYearUtil(LocalDateTime.parse(outTimeEntity.getBorrowTime(), formatter), LocalDateTime.now()));
            outTimeDtoList.add(bOD);
        }
        Page<borrowOutTimeDto> paging = new Page();
        BeanUtils.copyProperties(page, paging);
        paging.setRecords(outTimeDtoList);
        return paging;
    }

}
