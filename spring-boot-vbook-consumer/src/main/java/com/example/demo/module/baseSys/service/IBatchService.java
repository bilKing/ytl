package com.example.demo.module.baseSys.service;

import com.example.demo.common.Exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ytl on 2019/11/19.
 * <p>
 * 批量操作
 */
public interface IBatchService {

    //批量禁用用户
    void batchForbiddenConsumerUserService(String userGraduationYear, MultipartFile file, String userSpecialities) throws ServiceException;

    //批量注册
    void batchRegisterConsumerService(MultipartFile file) throws ServiceException;

    //批量添加图书
    void batchAddBookConsumerService(MultipartFile file) throws ServiceException;

}
