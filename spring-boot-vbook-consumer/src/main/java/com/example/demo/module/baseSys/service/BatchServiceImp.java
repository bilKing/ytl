package com.example.demo.module.baseSys.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.entity.baseEntity.bookEntity;
import com.example.demo.entity.baseEntity.userEntity;
import com.example.demo.module.baseSys.util.ExcelAddUtil;
import com.example.demo.service.baseService.ISysBookService;
import com.example.demo.service.baseService.ISysVbookService;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytl on 2019/11/19.
 * <p>
 * 批量操作
 */
@Service
public class BatchServiceImp implements IBatchService {

    @Reference(loadbalance = "roundrobin")
    ISysVbookService IsysVbookService;

    @Reference(loadbalance = "roundrobin")
    ISysBookService IsysBookService;

    //批量禁用用户
    @Override
    public void batchForbiddenConsumerUserService(String userGraduationYear, MultipartFile file, String userSpecialities) throws ServiceException {
        userEntity forbiddenUserEntity = new userEntity();
        forbiddenUserEntity.setState(Constant.PROHIBIT);
        //禁用时间
        forbiddenUserEntity.setForbiddenTime(LocalDateTimeToStringUtil.formatter());
        //判断是否上传文件
        if (null != file) {
            try {
                //判断文件格式
                FileFormatUtil.fileFormat(file);
                // 创建对Excel工作簿文件的引用
                List<String> exUser = new ArrayList<>();
                InputStream inputStream = file.getInputStream();
                POIFSFileSystem fs = new POIFSFileSystem(inputStream);
                HSSFWorkbook workbook = new HSSFWorkbook(fs);
                HSSFSheet sheet = workbook.getSheet("sheet1");
                //判断excel表是否为空
                if (sheet.getLastRowNum() != 0) {
                    for (int x = 1; x <= sheet.getLastRowNum(); x++) {
                        HSSFRow row = sheet.getRow((short) x);
                        HSSFCell cell = row.getCell(0);
                        //判断数据为空
                        if (cell == null || cell.getCellType() == CellType.BLANK) {
                            continue;
                        }
                        //将int学号转化为string
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                exUser.add(String.valueOf(PoiNumericUtil.Numeric(cell)));
                                break;
                            case STRING:
                                exUser.add(cell.getStringCellValue());
                                break;
                        }
                    }
                    IsysVbookService.batchForbiddenUserService(userGraduationYear, userSpecialities, exUser);
                }
            } catch (Exception e) {
                throw new ServiceException(ServiceExceptionEnum.FORBIDDEN_FALSE);
            }
        } else {
            IsysVbookService.batchForbiddenUserService(userGraduationYear, userSpecialities, null);
        }
    }

    //批量注册
    @Override
    public void batchRegisterConsumerService(MultipartFile file) throws ServiceException {
        List<userEntity> newUserEntities = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            HSSFSheet sheet = workbook.getSheet("sheet1");
            //判断excel表是否为空
            if (sheet.getLastRowNum() == 0) {
                throw new ServiceException(ServiceExceptionEnum.EXCEL_NULL);
            }
            for (int x = 1; x <= sheet.getLastRowNum(); x++) {
                userEntity newUserEntity = new userEntity();
                HSSFRow row = sheet.getRow((short) x);
                for (int y = 0; y < 6; y++) {
                    HSSFCell cell = row.getCell((short) y);
                    //判断数据为空
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        //如果密码为空则设置为默认密码
                        if (y == Constant.USER_PASSWORD) {
                            newUserEntity.setUserPassword(Constant.DEFAULT_PASSWORD);
                        }
                        //如果学号为空则抛异常
                        if (y == Constant.STU_ID) {
                            throw new ServiceException(ServiceExceptionEnum.STUID_NULL);
                        }
                        continue;
                    }
                    BeanUtils.copyProperties(ExcelAddUtil.excelUserAdd(y, cell), newUserEntity);
                    newUserEntity.setId(UUID.getUUID());
                    newUserEntity.setUserAuthority(Constant.user);
                }
                newUserEntities.add(newUserEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ServiceExceptionEnum.STUID_NULL_OR_PASSWORD_FALSE);
        }
        //批量分页插入
        int size = Constant.BATCH_INSERT;
        long totalPage = 1;
        int start = 0;
        int end = 0;
        //计算总页数
        if (newUserEntities.size() % size == 0) {
            totalPage = newUserEntities.size() / size;
        } else {
            totalPage = newUserEntities.size() / size + 1;
        }
        for (long current = 1; current <= totalPage; current++) {
            //插入
            if (start + size > newUserEntities.size()) {
                if (start - size < 0) {
                    IsysVbookService.batchRegisterService(newUserEntities);
                    break;
                }
                end = newUserEntities.size();
            }
            if (start + size <= newUserEntities.size()) {
                end = start + size;
            }
            List<userEntity> list = newUserEntities.subList(start,end);
            IsysVbookService.batchRegisterService(list);
            start = start + size;
        }
    }

    //批量添加图书
    @Override
    public void batchAddBookConsumerService(MultipartFile file) throws ServiceException {
        List<bookEntity> newBooks = new ArrayList<>();
        try {
            // 创建对Excel工作簿文件的引用
            InputStream inputStream = file.getInputStream();
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            HSSFSheet sheet = workbook.getSheet("sheet1");
            //判断excel表是否为空
            if (sheet.getLastRowNum() == 0) {
                throw new ServiceException(ServiceExceptionEnum.EXCEL_NULL);
            }
            for (int x = 1; x <= sheet.getLastRowNum(); x++) {
                bookEntity newBook = new bookEntity();
                HSSFRow row = sheet.getRow((short) x);
                for (int y = 0; y < 4; y++) {
                    HSSFCell cell = row.getCell((short) y);
                    //判断数据为空
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        //如果图书id为空
                        if (y == Constant.BOOK_ID) {
                            throw new ServiceException(ServiceExceptionEnum.BOOK_ID_NULL);
                        }
                        continue;
                    }
                    BeanUtils.copyProperties(ExcelAddUtil.excelBookAdd(y, cell), newBook);
                    //设置uuid,设置创建年份
                    newBook.setId(UUID.getUUID());
                    newBook.setBookAddYear(LocalDateTime.now().getYear());
                }
                newBooks.add(newBook);
            }
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionEnum.BOOK_ID_OR_ADDRESS_NULL);
        }
        //批量分页插入
        int size = 3;
        long totalPage = 1;
        int start = 0;
        int end = 0;
        //计算总页数
        if (newBooks.size() % size == 0) {
            totalPage = newBooks.size() / size;
        } else {
            totalPage = newBooks.size() / size + 1;
        }
        for (long current = 1; current <= totalPage; current++) {
            //插入
            if (start + size > newBooks.size()) {
                if (start - size < 0) {
                    IsysBookService.batchAddBookService(newBooks);
                    break;
                }
                end = newBooks.size();
            }
            if (start + size <= newBooks.size()) {
                end = start + size;
            }
            List<bookEntity> list = newBooks.subList(start,end);
            IsysBookService.batchAddBookService(list);
            start = start + size;
        }
    }

}
