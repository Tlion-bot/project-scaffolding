package com.base.test.project.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.test.common.utils.CreateAccessLogDataUtils;
import com.base.test.project.business.domain.SysAccessLog;
import com.base.test.project.business.mapper.SysAccessLogMapper;
import com.base.test.project.business.service.SysAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @Author chenqingshan
 * @Date 2023/7/5 13:55
 * @Description TODO
 **/
@Slf4j
@Service
public class SysAccessLogServiceImpl extends ServiceImpl<SysAccessLogMapper, SysAccessLog>  implements SysAccessLogService {

    @Autowired
    private SysAccessLogMapper sysAccessLogMapper;
    @Override
    @Async("threadPoolTaskExecutor")
    public void executeAsyncInsertSysAccessLog(CountDownLatch countDownLatch, int i,int size) {
        try {
            long start = System.currentTimeMillis();
            List<SysAccessLog> sysAccessLogList = new CopyOnWriteArrayList<>();
            log.info("线程" + Thread.currentThread().getId() + "开始执行");
            for (int j=0;j<size;j++){
                String ip = CreateAccessLogDataUtils.getRandomIp();
                String browser = CreateAccessLogDataUtils.getRandomBrowser();
                String os = CreateAccessLogDataUtils.getRandomOS();
                String url = CreateAccessLogDataUtils.getRandomUrl();
                String method = CreateAccessLogDataUtils.getRandomMethod();
                Date date = CreateAccessLogDataUtils.randomDate("2022-05-01 00:00:00","2023-07-05 23:59:59");
                if("Mac OS X".equals(os)){
                    browser = "Safari";
                }
                sysAccessLogList.add(new SysAccessLog(ip,browser,os,url,method,date));
                if(sysAccessLogList.size() == 50 || j == (size -1)){
                    saveBatch(sysAccessLogList);
                    sysAccessLogList.clear();
                }


            }
            long end = System.currentTimeMillis();
            //计算本线程运行完毕使用的时间，单位为秒
            long threadUsedTimeSecond=(end-start);
            log.info("线程" + Thread.currentThread().getId() + "执行结束,用时"+threadUsedTimeSecond+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程运行完毕，线程计数器-1
            countDownLatch.countDown();
        }
    }
    @Override
    public void insertSysAccessLog(SysAccessLog sysAccessLog) {
        sysAccessLogMapper.insertSysAccessLog(sysAccessLog);
    }
}
