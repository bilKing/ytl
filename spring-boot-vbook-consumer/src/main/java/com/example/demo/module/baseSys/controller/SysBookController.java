package com.example.demo.module.baseSys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.entity.baseEntity.bookEntity;
import com.example.demo.pojo.basePojo.form.bookBorrowForm;
import com.example.demo.pojo.basePojo.form.bookForm;
import com.example.demo.service.baseService.ISysBookService;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import com.example.demo.common.util.AssertUtil;
import com.example.demo.common.util.FileFormatUtil;
import com.example.demo.common.util.isEmptyUtil;
import com.example.demo.module.baseSys.service.IBatchService;
import com.example.demo.module.exSys.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 图书表控制器
 */
@RequestMapping(value = "/Book")
@RestController
public class SysBookController {

    @Reference(loadbalance = "roundrobin")
    ISysBookService service;

    @Autowired
    IExportService exportService;

    @Autowired
    private IBatchService IbatchService;

    //录入书籍
    @PostMapping(value = "/addBook")
    @AuthToken(role_name = Constant.bookManagement)
    public R addBook(@RequestBody bookForm form) {
        AssertUtil.AssertNotBlank(form.getBookId(), "未传输书籍信息");
        service.addBookService(form);
        return R.ok();
    }

    //批量录入书籍
    @PostMapping(value = "/batchAddBook")
    @AuthToken(role_name = Constant.bookManagement)
    public R batchAddBook(MultipartFile file) {
        //判断是否上传文件
        if (file == null) {
            throw new ServiceException(ServiceExceptionEnum.FILE_FALSE);
        }
        FileFormatUtil.fileFormat(file);
        IbatchService.batchAddBookConsumerService(file);
        return R.ok();
    }

    //批量录入书籍导出模板
    @PostMapping(value = "/batchAddBookTemplate")
    public void batchAddBookTemplate(HttpServletResponse resp) throws IOException {
        File directory = new File(Constant.NEW_BOOKS);
        exportService.streamService(resp, directory.getCanonicalPath(), false);
    }

    //禁用启用 书籍及借书记录
    @PostMapping(value = "/forbiddenBook")
    @AuthToken(role_name = Constant.bookManagement)
    public R forbiddenBook(@RequestBody HashMap<String, String> map) {
        String id = map.get("id");
        Boolean isName = Boolean.parseBoolean(map.get("isName"));
        int state = Integer.parseInt(map.get("state"));
        if (isEmptyUtil.isEmpty(isName) || isEmptyUtil.isEmpty(id)) {
            throw new ServiceException(ServiceExceptionEnum.BOOK_NAME_OR_ID);
        }
        service.forbiddenBookService(state, id, isName);
        return R.ok();
    }

    //删除n天前禁用书籍,并删除借书记录
    @GetMapping(value = "/deleteBook")
    @AuthToken(role_name = Constant.bookManagement)
    public R deleteBook() {
        service.deleteBookService();
        return R.ok();
    }

    //借书返回列表
    @PostMapping(value = "/borrowBookList")
    public R<Page<List<bookBorrowForm>>> borrowBookList(@RequestBody bookBorrowForm form) {
        return R.ok(service.borrowBookListService(form));
    }

    //管理员查看书籍信息列表
    @PostMapping(value = "/bookList")
    @AuthToken(role_name = Constant.bookManagement)
    public R<Page<List<bookEntity>>> bookList(@RequestBody bookBorrowForm form) {
        return R.ok(service.bookListService(form));
    }

}
