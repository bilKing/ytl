package com.example.demo.service.baseService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.pojo.basePojo.dto.bookReturnDto;
import com.example.demo.pojo.basePojo.form.bookReturnForm;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.form.borrowOutTimeForm;


/**
 * Created by ytl on 2019/10/17.
 * <p>
 * 借书表 baseService
 */
public interface ISysBookLendService {

    //借书
    void borrowBookService(String bookId) throws ServiceException;

    //还书
    int returnBookService(String id) throws ServiceException;

    //还书返回列表
    IPage<bookReturnDto> returnBookListService(String stuId, bookReturnForm form) throws ServiceException;

    //借书表中已还的书记录删除 并添加到借书记录路表中,删除借书表中已还的书籍
    void addToBorrowService() throws ServiceException;

    //统计超过n天借书未还的书本 和 学号
    IPage<borrowOutTimeDto> borrowOutTimeService(borrowOutTimeForm outTimeForm) throws ServiceException;

}
