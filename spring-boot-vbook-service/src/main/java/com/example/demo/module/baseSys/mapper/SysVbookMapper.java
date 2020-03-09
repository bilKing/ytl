package com.example.demo.module.baseSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.dto.roleDto;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.pojo.basePojo.form.userBorrowForm;
import com.example.demo.pojo.basePojo.form.userListForm;
import com.example.demo.common.pojo.Page;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.form.borrowSpecialitiesCountForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 用户表 mapper
 */
public interface SysVbookMapper extends BaseMapper<userEntity> {

    //批量添加用户
    void insertNewUsers(@Param("newUserEntities") List<userEntity> newUserEntities);

    //禁用借书记录
    void forbiddenBookBorrow(@Param("forbiddenBookBorrow") bookBorrowEntity forbiddenBookBorrow,
                             @Param("userEntities") List<userEntity> userEntities);

    //统计各专业，某预计毕业年份，某时间段内借书本数
    List<borrowSpecialitiesCountDto> selectBorrowCount(@Param("page") Page<borrowSpecialitiesCountDto> page,
                                                       @Param("form") borrowSpecialitiesCountForm form);

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）
    List<userBorrowDto> selectUserBorrow(@Param("page") Page<userBorrowDto> page,
                                         @Param("form") userBorrowForm form);

    //查询用户权限
    List<roleDto> selectRole(@Param("userAuthority") Integer userAuthority);

    //管理员查看用户信息
    List<userEntity> selectUserList(@Param("page") Page<userEntity> page,
                                    @Param("form") userListForm form);

}

