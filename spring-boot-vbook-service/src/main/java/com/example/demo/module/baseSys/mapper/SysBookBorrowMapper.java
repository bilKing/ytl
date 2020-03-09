package com.example.demo.module.baseSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.form.lendRecordForm;
import com.example.demo.common.pojo.Page;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;
import com.example.demo.pojo.exPojo.form.topLendBookForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书记录表 mapper
 */
public interface SysBookBorrowMapper extends BaseMapper<bookBorrowEntity> {

    //批量启用禁用的借书记录
    void updateBatchEnable(@Param("enableUserList") List<userEntity> enableUserList);

    //统计某专业，某预计毕业年份 借书次数最多的书籍（TOP n）
    List<topLendBookDto> topLendBookList(@Param("page") Page<topLendBookDto> page,
                                         @Param("form") topLendBookForm form);

    // 某学号或某图书号 的借书记录
    List<bookBorrowEntity> LendRecordList(@Param("page") Page<bookBorrowEntity> page,
                                          @Param("form") lendRecordForm form);

}
