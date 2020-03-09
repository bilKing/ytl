package com.example.demo.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by ytl on 2019/10/11.
 * <p>
 * poi number处理 工具类
 */
public class PoiNumericUtil {
    //转化numeric的int
    public static Object Numeric(HSSFCell cell) {
        long longVal = Math.round(cell.getNumericCellValue());
        if (Double.parseDouble(longVal + ".0") == cell.getNumericCellValue()) {
            return longVal;
        } else {
            return cell.getNumericCellValue();
        }
    }
}
