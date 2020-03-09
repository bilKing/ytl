package com.example.demo.common.util;

import java.io.File;

/**
 * Created by Administrator on 2019/11/5.
 * <p>
 * 删除文件 工具类
 */
public class DeleteFileUtil {
    public static void delete(String fileName) {
        //删除当前文件目录中的文件 如果无该文件则跳过
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
