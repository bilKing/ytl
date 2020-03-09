package com.example.demo.module.baseSys.util;

import com.example.demo.common.constant.Constant;
import com.example.demo.common.util.CheckPassword;
import com.example.demo.common.util.PoiToStringUtil;
import com.example.demo.pojo.basePojo.form.bookForm;
import com.example.demo.pojo.basePojo.form.registerForm;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by ytl on 2019/11/26.
 * <p>
 * Excel工具类
 */
public class ExcelAddUtil {

    //批量录入图书 工具类
    public static bookForm excelBookAdd(int y, HSSFCell cell) {
        String strInf = PoiToStringUtil.poiToString(cell);
        bookForm newBook = new bookForm();
        switch (y) {
            case Constant.BOOK_NAME:
                newBook.setBookName(strInf);
                break;
            case Constant.BOOK_ID:
                newBook.setBookId(strInf);
                break;
            case Constant.BOOK_TYPE:
                newBook.setBookType(strInf);
                break;
            case Constant.BOOK_PRICE:
                newBook.setBookPrice(strInf);
                break;
        }
        return newBook;
    }

    //批量注册 工具类
    public static registerForm excelUserAdd(int y, HSSFCell cell) {
        String strInf = PoiToStringUtil.poiToString(cell);
        registerForm newUser = new registerForm();
        switch (y) {
            case Constant.USER_NAME:
                newUser.setUserName(strInf);
                break;
            case Constant.USER_PASSWORD: {
                CheckPassword.checkPassword(strInf);
                newUser.setUserPassword(strInf);
            }
            break;
            case Constant.STU_ID:
                newUser.setStuId(strInf);
                break;
            case Constant.USER_CLASS:
                newUser.setUserClass(strInf);
                break;
            case Constant.SPECIALITIES:
                newUser.setUserSpecialities(strInf);
                break;
            case Constant.USER_GRADUATION_YEAR:
                newUser.setUserGraduationYear(Integer.parseInt(strInf));
                break;
        }
        return newUser;
    }

    //建立表头 工具类
    public static void excelTableHeadUtil(HSSFSheet sheet, String... args) {
        HSSFRow rowHead = sheet.createRow(0);
        for (int yH = 0; yH < args.length; yH++) {
            HSSFCell cellHead = rowHead.createCell(yH);
            cellHead.setCellType(CellType.STRING);
            cellHead.setCellValue(args[yH]);
        }
    }

}
