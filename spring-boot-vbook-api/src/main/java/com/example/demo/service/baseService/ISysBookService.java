package com.example.demo.service.baseService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.baseEntity.bookEntity;
import com.example.demo.pojo.basePojo.dto.bookBorrowDto;
import com.example.demo.pojo.basePojo.form.bookBorrowForm;
import com.example.demo.pojo.basePojo.form.bookForm;
import com.example.demo.common.Exception.ServiceException;

import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 图书表 baseService
 */
public interface ISysBookService {

    //添加图书
    void addBookService(bookForm form) throws ServiceException;

    //批量添加图书
    void batchAddBookService(List<bookEntity> list) throws ServiceException;

    //启用,禁用图书
    void forbiddenBookService(int state, String id, boolean isName) throws ServiceException;

    //删除图书
    void deleteBookService() throws ServiceException;

    //借书返回列表
    IPage<bookBorrowDto> borrowBookListService(bookBorrowForm form) throws ServiceException;

    //管理员查看书籍信息列表
    IPage<bookEntity> bookListService(bookBorrowForm form) throws ServiceException;

}
