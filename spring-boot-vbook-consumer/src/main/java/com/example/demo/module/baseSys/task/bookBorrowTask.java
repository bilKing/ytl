package com.example.demo.module.baseSys.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.service.baseService.ISysBookLendService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ytl on 2019/10/17.
 * <p>
 * 定时更新借书表中已还的借书记录 到 借书记录表
 */
@Component
public class bookBorrowTask {

    @Reference(loadbalance = "roundrobin")
    ISysBookLendService IsysBookLendService;

    //@Scheduled(cron = "0 0 1 * * ?")
    public void bookBorrowTask() {
        IsysBookLendService.addToBorrowService();
    }

}
