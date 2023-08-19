package com.base.test.project.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.test.project.business.domain.SysAccessLog;

import java.util.concurrent.CountDownLatch;

public interface SysAccessLogService extends IService<SysAccessLog> {

    public void executeAsyncInsertSysAccessLog(CountDownLatch countDownLatch, int i,int size);

    void insertSysAccessLog(SysAccessLog sysAccessLog);
}
