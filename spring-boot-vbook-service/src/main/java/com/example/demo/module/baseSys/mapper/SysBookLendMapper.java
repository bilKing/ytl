package com.example.demo.module.baseSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.bookLendEntity;
import com.example.demo.pojo.basePojo.dto.bookReturnDto;
import com.example.demo.common.pojo.Page;
import com.example.demo.pojo.exPojo.form.borrowOutTimeForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/10/17.
 * <p>
 * 借书表 mapper
 */
public interface SysBookLendMapper extends BaseMapper<bookLendEntity> {

    //搜索书名
    String selectBookName(@Param("bookId") String bookId);

    //更新图书表借书信息
    void updateBorrowBook(@Param("newBorrow") bookLendEntity newBorrow);

    //判断书是否被借出
    int selectLend(@Param("bookId") String bookId);

    //更新图书表借书信息
    void updateReturnBook(@Param("returnBook") bookLendEntity returnBook);

    //还书返回列表
    List<bookReturnDto> selectBookReturnList(@Param("page") Page<bookReturnDto> page,
                                             @Param("stuId") String stuId);

    //搜索借书表中已还的书籍
    List<bookBorrowEntity> alreadyRepaidList();

    //将借书记录添加到 借书记录表
    void insertAlreadyRepaidList(@Param("list") List<bookBorrowEntity> list);

    //统计超过n天借书未还的书本 和 学号
    List<bookBorrowEntity> selectOutTimeBorrowList(@Param("outTimeForm") borrowOutTimeForm outTimeForm,
                                                   @Param("targetTime") String targetTime,
                                                   @Param("page") Page<bookBorrowEntity> page);

}
