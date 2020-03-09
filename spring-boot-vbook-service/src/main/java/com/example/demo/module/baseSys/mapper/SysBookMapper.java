package com.example.demo.module.baseSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.baseEntity.bookEntity;
import com.example.demo.pojo.basePojo.dto.bookBorrowDto;
import com.example.demo.pojo.basePojo.form.bookBorrowForm;
import com.example.demo.common.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 图书表 mapper
 */
public interface SysBookMapper extends BaseMapper<bookEntity> {

    //批量添加书籍
    void insertNewBooks(@Param("list") List<bookEntity> list);

    //查找书籍
    List<bookBorrowDto> selectBookBorrowList(@Param("page") Page<bookBorrowDto> page,
                                             @Param("form") bookBorrowForm form);

    //管理员查看书籍信息列表
    List<bookEntity> selectBookList(@Param("page") Page<bookEntity> page,
                                    @Param("form") bookBorrowForm form);

}
