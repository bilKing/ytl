package com.example.demo.service.baseService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.form.lendRecordForm;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;
import com.example.demo.pojo.exPojo.form.topLendBookForm;

import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书记录表 baseService
 */
public interface ISysBookBorrowService {

    //按学号或图书号禁用借书记录(由图书表和用户表Service调用)
    void forbiddenBookBorrowAsStuOrBookId(int state, int type, String stuOrBookId) throws ServiceException;

    //删除借书记录(由图书表和用户表Service调用)
    void deleteBookBorrow() throws ServiceException;

    //批量启用禁用的借书记录
    void batchEnable(List<userEntity> enableUserList) throws ServiceException;

    //统计某专业，某预计毕业年份借书次数最多的书籍（TOP n）
    IPage<topLendBookDto> topLendBook(topLendBookForm form) throws ServiceException;

    // 某学号或某图书号 的借书记录
    IPage<bookBorrowEntity> LendRecordListService(lendRecordForm form) throws ServiceException;

}
