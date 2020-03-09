package com.example.demo.common.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ytl on 2019/10/16.
 *
 * 文件流 工具类
 */
public class FileOutputStreamUtil {
    public static void fileOutputStreamUtil(String outputFile, HSSFWorkbook workbook) throws IOException {

        // 新建一输出文件流
        FileOutputStream fOut = new FileOutputStream(outputFile);

        // 把相应的Excel 工作簿存盘
        workbook.write(fOut);
        fOut.flush();

        // 操作结束，关闭文件
        fOut.close();
    }

}
