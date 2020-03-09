package com.example.demo.service.baseService;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.dto.roleDto;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.pojo.basePojo.form.*;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.form.borrowSpecialitiesCountForm;

import java.util.List;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 用户表 baseService
 */
public interface ISysVbookService {

    //注册
    void registerService(registerForm form) throws ServiceException;

    //批量注册
    void batchRegisterService(List<userEntity> newUserEntities) throws ServiceException;

    //登录
    void loginService(loginForm form) throws ServiceException;

    //禁用用户
    void forbiddenUserService(int state, String stuId) throws ServiceException;

    //批量禁用用户
    void batchForbiddenUserService(String userSpecialities, String userGraduationYear, List<String> exUser) throws ServiceException;

    //批量启用用户
    void batchEnableUserService(String userSpecialities, String userGraduationYear) throws ServiceException;

    //删除n天前禁用的用户
    void deleteForbiddenUser() throws ServiceException;

    //统计各专业，某预计毕业年份，某时间段内借书本数
    IPage<borrowSpecialitiesCountDto> borrowCountService(borrowSpecialitiesCountForm form) throws ServiceException;

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）
    IPage<userBorrowDto> userBorrowCountService(userBorrowForm form) throws ServiceException;

    //用户权限修改
    void changeRoleService(int isAdd, String stuId, int userAuthority) throws ServiceException;

    //用户信息修改
    void personaDataChangeService(personaDataChangeForm form) throws ServiceException;

    //查询用户权限
    List<roleDto> selectRoleService(String stuId) throws ServiceException;

    //搜索个人信息，配合完善个人资料
    userEntity selectPersonalInfService(String stuId) throws ServiceException;

    //管理员查看用户信息
    IPage<userEntity> userListService(userListForm form) throws ServiceException;

}
