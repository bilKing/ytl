package com.example.demo.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * poi导入判断number或string
 */
public class PoiToStringUtil {
    public static String poiToString(HSSFCell cell) {
        Object inf = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                inf = PoiNumericUtil.Numeric(cell);
                break;
            case STRING:
                inf = cell.getStringCellValue();
                break;
        }
        return inf.toString();
    }
}
