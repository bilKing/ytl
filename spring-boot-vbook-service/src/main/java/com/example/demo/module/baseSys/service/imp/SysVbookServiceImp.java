package com.example.demo.module.baseSys.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.entity.baseEntity.bookBorrowEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.dto.roleDto;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.pojo.basePojo.form.*;
import com.example.demo.service.baseService.ISysBookBorrowService;
import com.example.demo.service.baseService.ISysVbookService;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.Session;
import com.example.demo.common.util.*;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.form.borrowSpecialitiesCountForm;
import com.example.demo.module.baseSys.mapper.SysVbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 用户表 baseService
 */
@org.springframework.stereotype.Service("ISysVbookService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysVbookService.class)
public class SysVbookServiceImp implements ISysVbookService {

    @Autowired
    SysVbookMapper SysVbookMapper;

    @Autowired
    Session session;

    @Autowired
    ErrorPasswordUtil Error;

    @Autowired
    ISysBookBorrowService IsysBookBorrowService;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor executor;

    //注册
    @Override
    @Transactional
    public void registerService(registerForm form) throws ServiceException {
        //判断是否已经注册
        QueryWrapper<userEntity> reQueryWrapper = new QueryWrapper<>();
        reQueryWrapper.eq("stu_id", form.getStuId());
        if (isEmptyUtil.isNotEmpty(SysVbookMapper.selectOne(reQueryWrapper))) {
            throw new ServiceException(ServiceExceptionEnum.STUID_ISEXISTENCE);
        }
        //插入新用户
        userEntity newUserEntity = new userEntity();
        newUserEntity.setUserAuthority(Constant.user);
        newUserEntity.setId(UUID.getUUID());
        BeanUtils.copyProperties(form, newUserEntity);
        SysVbookMapper.insert(newUserEntity);
    }

    //批量注册
    @Override
    @Transactional
    public void batchRegisterService(List<userEntity> newUserEntities) {
        SysVbookMapper.insertNewUsers(newUserEntities);
    }

    //登录
    @Override
    public void loginService(loginForm form) throws ServiceException {
        //判断密码错误 次数 等待 时间是否满足配置
        Error.beforeLogin();
        //查询学号和密码是否相同
        QueryWrapper<userEntity> lgQueryWrapper = new QueryWrapper<>();
        lgQueryWrapper.eq("stu_id", form.getStuId())
                .eq("user_password", form.getUserPassword())
                .eq("state", 1);
        userEntity user = SysVbookMapper.selectOne(lgQueryWrapper);
        if (isEmptyUtil.isEmpty(user)) {
            //密码错误 错误次数+1 记录错误时间 抛出密码错误异常
            Error.beforeErrorPassword();
        }
        session.setStuId(user.getStuId());
        session.setUserAuthority(String.valueOf(user.getUserAuthority()));
        try {
            session.setUserName(user.getUserName());
        } catch (RuntimeException e) { }
    }

    //禁用用户
    @Override
    @Transactional
    public void forbiddenUserService(int state, String stuId) throws ServiceException {
        userEntity forbiddenUserEntity = new userEntity();
        forbiddenUserEntity.setState(state);
        if (state == 0) {
            forbiddenUserEntity.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        }
        UpdateWrapper<userEntity> FW = new UpdateWrapper<>();
        FW.eq("stu_id", stuId);
        //禁用借书记录
        IsysBookBorrowService.forbiddenBookBorrowAsStuOrBookId(state, Constant.IS_STU_ID, stuId);
        //禁用借书表
        executor.execute(() -> SysVbookMapper.update(forbiddenUserEntity, FW));
    }

    //批量禁用用户
    @Override
    @Transactional
    public void batchForbiddenUserService(String userGraduationYear, String userSpecialities, List<String> exUser) {
        userEntity forbiddenUserEntity = new userEntity();
        forbiddenUserEntity.setState(Constant.PROHIBIT);
        //禁用时间
        forbiddenUserEntity.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        //禁用为筛选的该年份下该专业的用户
        UpdateWrapper<userEntity> FExW = new UpdateWrapper<>();
        FExW.eq("user_graduation_year", userGraduationYear)
                .eq("user_specialities", userSpecialities);
        //判断是否上传文件
        if (null != exUser) {
            FExW.notIn("stu_id", exUser);
        }
        SysVbookMapper.update(forbiddenUserEntity, FExW);
        //搜索禁用用户,禁用其借书记录
        bookBorrowEntity forbiddenBookBorrow = new bookBorrowEntity();
        forbiddenBookBorrow.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        List<userEntity> forbiddenUsers = SysVbookMapper.selectList(FExW);
        if (isEmptyUtil.isNotEmpty(forbiddenUsers)) {
            SysVbookMapper.forbiddenBookBorrow(forbiddenBookBorrow, forbiddenUsers);
        }
    }

    //批量启用用户
    @Override
    @Transactional
    public void batchEnableUserService(String userSpecialities, String userGraduationYear) throws ServiceException {
        userEntity enableUserEntity = new userEntity();
        enableUserEntity.setState(Constant.ENABLE);
        UpdateWrapper<userEntity> bEUW = new UpdateWrapper<>();
        bEUW.eq("user_graduation_year", userGraduationYear)
                .eq("user_specialities", userSpecialities)
                .eq("state", 0);
        //批量启用被禁用的借书记录
        IsysBookBorrowService.batchEnable(SysVbookMapper.selectList(bEUW));
        //更新用戶表
        executor.execute(() -> SysVbookMapper.update(enableUserEntity, bEUW));
    }

    //删除n天前禁用的用户
    @Override
    @Transactional
    public void deleteForbiddenUser() {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<userEntity> dFUW = new QueryWrapper<>();
        dFUW.le("forbidden_time", now.plusDays(-Constant.DELETE_FORBIDDEN_USER_DAY))
                .eq("state", 0);
        SysVbookMapper.delete(dFUW);
        //删除借书表信息
        executor.execute(() -> IsysBookBorrowService.deleteBookBorrow());
    }

    //统计各专业，某预计毕业年份，某时间段内借书本数
    @Override
    public IPage<borrowSpecialitiesCountDto> borrowCountService(borrowSpecialitiesCountForm form) throws ServiceException {
        Page<borrowSpecialitiesCountDto> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(SysVbookMapper.selectBorrowCount(page, form));
        return page;
    }

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）
    @Override
    public IPage<userBorrowDto> userBorrowCountService(userBorrowForm form) throws ServiceException {
        Page<userBorrowDto> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(SysVbookMapper.selectUserBorrow(page, form));
        return page;
    }

    //查询用户权限
    @Override
    public List<roleDto> selectRoleService(String stuId) throws ServiceException {
        QueryWrapper<userEntity> sRW = new QueryWrapper<>();
        sRW.eq("stu_id", stuId)
                .eq("state", 1);
        userEntity userRole = SysVbookMapper.selectOne(sRW);
        if (isEmptyUtil.isEmpty(userRole)) {
            throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
        }
        return SysVbookMapper.selectRole(userRole.getUserAuthority());
    }

    //搜索个人信息，配合完善个人资料
    @Override
    public userEntity selectPersonalInfService(String stuId) throws ServiceException {
        QueryWrapper<userEntity> sPIW = new QueryWrapper<>();
        sPIW.eq("stu_id", stuId)
                .eq("state", 1);
        userEntity userInf = SysVbookMapper.selectOne(sPIW);
        if (isEmptyUtil.isEmpty(userInf)) {
            throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
        }
        return userInf;
    }

    //管理员查看用户信息
    @Override
    public IPage<userEntity> userListService(userListForm form) throws ServiceException {
        Page<userEntity> page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getCurrent());
        page.setRecords(SysVbookMapper.selectUserList(page, form));
        return page;
    }

    //用户权限修改
    @Override
    @Transactional
    public void changeRoleService(int isAdd, String stuId, int userAuthority) throws ServiceException {
        UpdateWrapper<userEntity> cRW = new UpdateWrapper<>();
        cRW.eq("stu_id", stuId)
                .eq("state", 1);
        userEntity role = SysVbookMapper.selectOne(cRW);
        //判读用户是否存在
        if (isEmptyUtil.isEmpty(role)) {
            throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
        }
        Integer userOldAuthority = role.getUserAuthority();
        //增加权限
        if (isAdd == 1) {
            if ((userOldAuthority & userAuthority) == userAuthority) {
                throw new ServiceException(ServiceExceptionEnum.THIS_PERMISSIONS_EXISTENCE);
            } else {
                role.setUserAuthority(userOldAuthority | userAuthority);
                SysVbookMapper.update(role, cRW);
            }
        } else {
            //删除权限
            if ((userOldAuthority & userAuthority) == userAuthority) {
                role.setUserAuthority(userOldAuthority - userAuthority);
                SysVbookMapper.update(role, cRW);
            } else {
                throw new ServiceException(ServiceExceptionEnum.THIS_PERMISSIONS_NULL);
            }
        }
    }

    //用户信息修改
    @Override
    @Transactional
    public void personaDataChangeService(personaDataChangeForm form) throws ServiceException {
        //搜索是否有这个stuId
        UpdateWrapper<userEntity> pDCWP = new UpdateWrapper<>();
        pDCWP.eq("stu_id", form.getStuId())
                .eq("state", 1);
        userEntity userInf = SysVbookMapper.selectOne(pDCWP);
        if (isEmptyUtil.isEmpty(userInf)) {
            throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
        }
        //密码是否符合规则
        if (isEmptyUtil.isNotEmpty(form.getUserPassword())) {
            CheckPassword.checkPassword(form.getUserPassword());
        }
        //修改用户信息
        BeanUtils.copyProperties(form, userInf);
        SysVbookMapper.update(userInf, pDCWP);
    }

}
