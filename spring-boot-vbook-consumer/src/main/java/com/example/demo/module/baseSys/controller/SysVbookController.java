package com.example.demo.module.baseSys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.pojo.basePojo.dto.roleDto;
import com.example.demo.pojo.basePojo.form.loginForm;
import com.example.demo.pojo.basePojo.form.personaDataChangeForm;
import com.example.demo.pojo.basePojo.form.registerForm;
import com.example.demo.pojo.basePojo.form.userListForm;
import com.example.demo.service.baseService.ISysBookLendService;
import com.example.demo.service.baseService.ISysVbookService;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import com.example.demo.common.pojo.Session;
import com.example.demo.common.util.*;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.form.borrowOutTimeForm;
import com.example.demo.module.baseSys.service.IBatchService;
import com.example.demo.module.exSys.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 用户表 控制器
 */
@RequestMapping(value = "/Vbook")
@RestController
public class SysVbookController {

    @Reference(loadbalance = "roundrobin")
    private ISysVbookService service;

    @Autowired
    private IBatchService IbatchService;

    @Autowired
    private IExportService exportService;

    @Autowired
    Session session;

    @Reference(loadbalance = "roundrobin")
    ISysBookLendService IsysBookLendService;

    //注册
    @PostMapping(value = "/register")
    public R register(@RequestBody registerForm form) {
        AssertUtil.AssertNotBlank(form.getStuId(), "未输入用户id");
        AssertUtil.AssertNotBlank(form.getUserPassword(), "未输入用户密码");
        CheckPassword.checkPassword(form.getUserPassword());
        service.registerService(form);
        return R.ok();
    }

    //批量注册
    @PostMapping(value = "/batchRegister")
    @AuthToken(role_name = Constant.userManagement)
    public R batchRegister(MultipartFile file) {
        //判断是否上传文件
        if (file == null) {
            throw new ServiceException(ServiceExceptionEnum.FILE_FALSE);
        }
        FileFormatUtil.fileFormat(file);
        IbatchService.batchRegisterConsumerService(file);
        return R.ok();
    }

    //批量注册导出模板
    @PostMapping(value = "/batchRegisterTemplate")
    public void batchRegisterTemplate(HttpServletResponse resp) throws IOException {
        File directory = new File(Constant.NEW_USERS);
        exportService.streamService(resp, directory.getCanonicalPath(), false);
    }

    //登录
    @PostMapping(value = "/login")
    public R login(@RequestBody loginForm form) {
        AssertUtil.AssertNotBlank(form.getStuId(), "未输入用户id");
        AssertUtil.AssertNotBlank(form.getUserPassword(), "未输入用户密码");
        service.loginService(form);
        //提醒用户超期n天借书未还
        borrowOutTimeForm outTimeForm = new borrowOutTimeForm();
        outTimeForm.setStuId(form.getStuId());
        IPage<borrowOutTimeDto> outTime = IsysBookLendService.borrowOutTimeService(outTimeForm);
        if (isEmptyUtil.isNotEmpty(outTime.getRecords())) {
            return R.ok("real");
        }
        return R.ok();
    }

    //获取用户名
    @GetMapping(value = "/getName")
    public R getName() {
        return R.ok(session.getUserName());
    }

    //退出登录
    @GetMapping(value = "/outlogin")
    public R outlogin() {
        session.removeStuId();
        session.removeUserAuthority();
        session.removeUserName();
        return R.ok();
    }

    //禁用启用 用户及借书记录
    @PostMapping(value = "/forbiddenUser")
    @AuthToken(role_name = Constant.userManagement)
    public R forbiddenUser(@RequestBody HashMap<String, String> map) {
        String stuId = map.get("stuId");
        AssertUtil.AssertNotBlank(stuId, "未输入用户id");
        service.forbiddenUserService(Integer.parseInt(map.get("state")), stuId);
        return R.ok();
    }

    //批量禁用某专业，某预计毕业年份下的某专业的用户，,并禁用借书记录
    @PostMapping(value = "/batchForbiddenUser")
    @AuthToken(role_name = Constant.userManagement)
    public R batchForbiddenUser(String userGraduationYear, String userSpecialities, MultipartFile file) {
        AssertUtil.AssertNotBlank(userSpecialities, "未输入专业");
        AssertUtil.AssertNotBlank(userGraduationYear, "未输入预计毕业年份");
        AssertUtil.isYear(Integer.parseInt(userGraduationYear));
        IbatchService.batchForbiddenConsumerUserService(userGraduationYear, file, userSpecialities);
        return R.ok();
    }

    //批量禁用 不禁用的用户 导出模板
    @PostMapping(value = "/batchForbiddenUserTemplate")
    public void batchForbiddenUserTemplate(HttpServletResponse resp) throws IOException {
        File directory = new File(Constant.EXTRA_FORBIDDEN_USERS);
        exportService.streamService(resp, directory.getCanonicalPath(), false);
    }

    //批量启用 某专业，某预计毕业年份的用户
    @PostMapping(value = "/batchEnableUser")
    @AuthToken(role_name = Constant.userManagement)
    public R batchEnableUser(@RequestBody HashMap<String, String> map) {
        String userGraduationYear = map.get("userGraduationYear");
        String userSpecialities = map.get("userSpecialities");
        AssertUtil.AssertNotBlank(userGraduationYear, "未输入预计毕业年份");
        AssertUtil.isYear(Integer.parseInt(userGraduationYear));
        AssertUtil.AssertNotBlank(userSpecialities, "未输入专业");
        service.batchEnableUserService(userSpecialities, userGraduationYear);
        return R.ok();
    }

    //删除 n天前禁用的 用户,并删除借书记录
    @GetMapping(value = "/deleteForbiddenUser")
    @AuthToken(role_name = Constant.userManagement)
    public R deleteForbiddenUser() {
        service.deleteForbiddenUser();
        return R.ok();
    }

    //查询用户权限
    @PostMapping(value = "/selectRole")
    @AuthToken(role_name = Constant.jurisdiction)
    public R<List<roleDto>> selectRole(@RequestBody HashMap<String, String> map) {
        String stuId = map.get("stuId");
        if (isEmptyUtil.isEmpty(stuId)) {
            throw new ServiceException(ServiceExceptionEnum.USER_EMPTY);
        }
        return R.ok(service.selectRoleService(stuId));
    }

    //用户权限修改
    @PostMapping(value = "/changeRole")
    @AuthToken(role_name = Constant.jurisdiction)
    public R changeRole(@RequestBody HashMap<String, String> map) {
        service.changeRoleService(Integer.parseInt(map.get("isAdd")), map.get("stuId"), Integer.parseInt(map.get("userAuthority")));
        return R.ok();
    }

    //用户信息修改
    @PostMapping(value = "/personaDataChange")
    @AuthToken(role_name = Constant.userManagement)
    public R personaDataChange(@RequestBody personaDataChangeForm form) {
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        service.personaDataChangeService(form);
        return R.ok();
    }

    //搜索个人信息，配合完善个人资料
    @GetMapping(value = "/selectPersonalInf")
    public R<userEntity> selectPersonalInf() {
        return R.ok(service.selectPersonalInfService(session.getStuId()));
    }

    //完善个人资料
    @PostMapping(value = "/personaDataPerfect")
    public R personaDataPerfect(@RequestBody personaDataChangeForm form) {
        form.setStuId(session.getStuId());
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        service.personaDataChangeService(form);
        return R.ok();
    }

    //搜索个人信息，配合修改用户信息
    @PostMapping(value = "/userInfo")
    public R<userEntity> userInfo(@RequestBody HashMap<String, String> map) {
        return R.ok(service.selectPersonalInfService(map.get("stuId")));
    }

    //管理员查看用户信息
    @PostMapping(value = "/userList")
    @AuthToken(role_name = Constant.userManagement)
    public R<Page<List<userEntity>>> userList(@RequestBody userListForm form) {
        if (isEmptyUtil.isNotEmpty(form.getUserGraduationYear())) {
            AssertUtil.isYear(form.getUserGraduationYear());
        }
        return R.ok(service.userListService(form));
    }

}
