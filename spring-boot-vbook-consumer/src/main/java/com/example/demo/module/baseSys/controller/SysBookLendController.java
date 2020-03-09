package com.example.demo.module.baseSys.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.pojo.basePojo.dto.bookReturnDto;
import com.example.demo.pojo.basePojo.form.bookReturnForm;
import com.example.demo.service.baseService.ISysBookLendService;
import com.example.demo.common.AuthToken.AuthToken;
import com.example.demo.common.constant.Constant;
import com.example.demo.common.pojo.Page;
import com.example.demo.common.pojo.R;
import com.example.demo.common.pojo.Session;
import com.example.demo.common.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ytl on 2019/10/15.
 * <p>
 * 借书表 控制器
 */
@RequestMapping(value = "/Lend")
@RestController
public class SysBookLendController {

    @Autowired
    Session session;

    @Reference(loadbalance = "roundrobin")
    ISysBookLendService IsysBookLendService;

    //借书
    @PostMapping(value = "/borrowBook")
    public R borrowBook(@RequestBody HashMap<String, String> map) {
        String bookId = map.get("bookId");
        AssertUtil.AssertNotBlank(bookId, "没有此书籍信息");
        IsysBookLendService.borrowBookService(bookId);
        return R.ok();
    }

    //还书
    @PostMapping(value = "/returnBook")
    public R returnBook(@RequestBody HashMap<String, String> map) {
        String id = map.get("id");
        AssertUtil.AssertNotBlank(id, "没有此书籍借书信息");
        return R.ok(IsysBookLendService.returnBookService(id));
    }

    //还书返回列表
    @PostMapping(value = "/returnBookList")
    public R<Page<List<bookReturnDto>>> returnBookList(@RequestBody bookReturnForm form) {
        return R.ok(IsysBookLendService.returnBookListService(session.getStuId(), form));
    }

    //借书表中已还的书记录删除 并添加到借书记录路表中
    @GetMapping(value = "/addToBorrow")
    @AuthToken(role_name = Constant.bookManagement)
    public R addToBorrow() {
        IsysBookLendService.addToBorrowService();
        return R.ok();
    }

}
