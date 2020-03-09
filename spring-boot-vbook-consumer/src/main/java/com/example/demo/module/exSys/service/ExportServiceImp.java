package com.example.demo.module.exSys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.module.baseSys.util.ExcelAddUtil;
import com.example.demo.pojo.basePojo.dto.userBorrowDto;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.util.DeleteFileUtil;
import com.example.demo.common.util.FileOutputStreamUtil;
import com.example.demo.pojo.exPojo.dto.borrowOutTimeDto;
import com.example.demo.pojo.exPojo.dto.borrowSpecialitiesCountDto;
import com.example.demo.pojo.exPojo.dto.topLendBookDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

/**
 * Created by ytl on 2019/10/16.
 * <p>
 * 统计表service
 */
@Service
public class ExportServiceImp implements IExportService {

    @Override
    //统计各专业，某预计毕业年份，某时间段内借书本数（借书时间大于 n天）-----导出
    public void borrowCountExportService(IPage<borrowSpecialitiesCountDto> borrowSpecialitiesCountDtoIPage) {
        //删除上次建立的文件
        DeleteFileUtil.delete(Constant.BORROW_COUNT);
        //路径
        String outputFile = Constant.BORROW_COUNT;
        try {
            // 创建新的Excel 工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            //表头
            ExcelAddUtil.excelTableHeadUtil(sheet, "专业名", "借书本数");
            //数据
            int row = 1;//数据初始行
            Iterator<borrowSpecialitiesCountDto> iter = borrowSpecialitiesCountDtoIPage.getRecords().iterator();
            while (iter.hasNext()) {
                borrowSpecialitiesCountDto dto = iter.next();
                HSSFRow rowHss = sheet.createRow((short) row);
                for (int yD = 0; yD < 2; yD++) {
                    HSSFCell cellD = rowHss.createCell(yD);
                    if (yD == 0) {
                        cellD.setCellValue(dto.getUserSpecialities());
                    }
                    if (yD == 1) {
                        cellD.setCellValue(dto.getBookNum());
                    }
                }
                row++;
            }
            //创建输出文件流
            FileOutputStreamUtil.fileOutputStreamUtil(outputFile, workbook);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionEnum.EXPORT_ERROR);
        }
    }

    @Override
    //统计某专业，某预计毕业年份，某时间内 每个学生 借书本数（借书时间大于 n天）-----导出
    public void userBorrowCountExportService(IPage<userBorrowDto> userBorrowDtoIPage) {
        //删除上次建立的文件
        DeleteFileUtil.delete(Constant.USER_BORROW_COUNT);
        //路径
        String outputFile = Constant.USER_BORROW_COUNT;
        try {
            // 创建新的Excel 工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            //表头
            ExcelAddUtil.excelTableHeadUtil(sheet, "学号", "借书本数");
            //数据
            int row = 1;//数据初始行
            Iterator<userBorrowDto> iter = userBorrowDtoIPage.getRecords().iterator();
            while (iter.hasNext()) {
                userBorrowDto dto = iter.next();
                HSSFRow rowHss = sheet.createRow((short) row);
                for (int yD = 0; yD < 2; yD++) {
                    HSSFCell cellD = rowHss.createCell(yD);
                    if (yD == 0) {
                        cellD.setCellValue(dto.getStuId());
                    }
                    if (yD == 1) {
                        cellD.setCellValue(dto.getBookNum());
                    }
                }
                row++;
            }
            //创建输出文件流
            FileOutputStreamUtil.fileOutputStreamUtil(outputFile, workbook);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionEnum.EXPORT_ERROR);
        }
    }

    @Override
    //统计超过n天借书未还的 书本和学号和借书时间 -----导出
    public void borrowOutTimeExportService(IPage<borrowOutTimeDto> borrowOutTimeDtoIPage) {
        //删除上次建立的文件
        DeleteFileUtil.delete(Constant.BORROW_OUT_TIME_EXPORT);
        //路径
        String outputFile = Constant.BORROW_OUT_TIME_EXPORT;
        try {
            // 创建新的Excel 工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            //表头
            ExcelAddUtil.excelTableHeadUtil(sheet, "学号", "图书号", "借书天数");
            //数据
            int row = 1;//数据初始行
            Iterator<borrowOutTimeDto> iter = borrowOutTimeDtoIPage.getRecords().iterator();
            while (iter.hasNext()) {
                borrowOutTimeDto dto = iter.next();
                HSSFRow rowHss = sheet.createRow((short) row);
                for (int yD = 0; yD < 3; yD++) {
                    HSSFCell cellD = rowHss.createCell(yD);
                    if (yD == 0) {
                        cellD.setCellValue(dto.getStuId());
                    }
                    if (yD == 1) {
                        cellD.setCellValue(dto.getBookId());
                    }
                    if (yD == 2) {
                        cellD.setCellValue(dto.getBorrowDay());
                    }
                }
                row++;
            }
            //创建输出文件流
            FileOutputStreamUtil.fileOutputStreamUtil(outputFile, workbook);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionEnum.EXPORT_ERROR);
        }
    }

    @Override
    //统计某专业，某预计毕业年份借书次数最多的书籍名字和数量 -----导出
    public void topLendBookExportService(IPage<topLendBookDto> topLendBookDtoIPage) {
        //删除上次建立的文件
        DeleteFileUtil.delete(Constant.TOP_LEND_BOOK);
        //路径
        String outputFile = Constant.TOP_LEND_BOOK;
        try {
            // 创建新的Excel 工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            //表头
            ExcelAddUtil.excelTableHeadUtil(sheet, "图书名", "借出次数");
            //数据
            int row = 1;//数据初始行
            Iterator<topLendBookDto> iter = topLendBookDtoIPage.getRecords().iterator();
            while (iter.hasNext()) {
                topLendBookDto dto = iter.next();
                HSSFRow rowHss = sheet.createRow((short) row);
                for (int yD = 0; yD < 2; yD++) {
                    HSSFCell cellD = rowHss.createCell(yD);
                    if (yD == 0) {
                        cellD.setCellValue(dto.getBookName());
                    }
                    if (yD == 1) {
                        cellD.setCellValue(dto.getLendTimes());
                    }
                }
                row++;
            }
            //创建输出文件流
            FileOutputStreamUtil.fileOutputStreamUtil(outputFile, workbook);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionEnum.EXPORT_ERROR);
        }
    }

    @Override
    //将文件转成流发送前端
    public void streamService(HttpServletResponse resp, String fileName, boolean delete) {
        File file = new File(fileName);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        resp.setHeader("Content-Disposition", "newUsers");
        byte[] buff = new byte[8192];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new ServiceException(ServiceExceptionEnum.FILE_DOWN_LOAD_FALSE);
                }
            }
        }
        if (delete) {
            //删除上次建立的文件
            DeleteFileUtil.delete(fileName);
        }
    }

}
