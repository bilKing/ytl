package com.example.demo.common.util;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ytl on 2019/11/4.
 * <p>
 * 检验文件格式
 */
public class FileFormatUtil {
    public static void fileFormat(MultipartFile file) {
        //判断文件类型是否是xls
        if (!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length()).endsWith(".xls")) {
            throw new ServiceException(ServiceExceptionEnum.FILE_FORMAT_FALSE);
        }
    }
}
