package com.example.demo.module.exSys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ytl on 2019/10/16.
 * <p>
 * 统计表service
 */
public interface IExportService {

    //统计各专业，某预计毕业年份，某时间段内借书本数（借书时间大于 n天）-----导出
    void borrowCountExportService(IPage<borrowSpecialitiesCountDto> borrowSpecialitiesCountDtoIPage) throws ServiceException;

    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）-----导出
    void userBorrowCountExportService(IPage<userBorrowDto> userBorrowDtoIPage) throws ServiceException;

    //统计超过n天借书未还的 书本和学号和借书时间 -----导出
    void borrowOutTimeExportService(IPage<borrowOutTimeDto> borrowOutTimeDtoIPage) throws ServiceException;

    //统计某专业，某预计毕业年份借书次数最多的书籍名字和数量 -----导出
    void topLendBookExportService(IPage<topLendBookDto> topLendBookDtoIPage) throws ServiceException;

    //将文件转成流发送前端
    void streamService(HttpServletResponse resp, String fileName, boolean delete) throws ServiceException;

}
