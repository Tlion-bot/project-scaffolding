package com.base.test.project.business.controller.manager;

import cn.hutool.core.date.DateUtil;
import com.base.test.common.annotation.RateLimit;
import com.base.test.common.controller.BaseController;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.manager.AsyncManager;
import com.base.test.common.manager.factory.AsyncFactory;
import com.base.test.project.business.service.SysAccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;



@Slf4j
@RequiredArgsConstructor
@RestController
public class SysAccessLogController extends BaseController {

    @Autowired
    private SysAccessLogService sysAccessLogService;

    /**
     * 初始化访问记录
     *
     * @return 结果
     */
    @GetMapping("/initSysAccessLogData/{size}")
    public AjaxResult initSysAccessLogData(@PathVariable(value = "size") int size) throws Exception {
        if (size % 5 != 0) {
            AjaxResult.success("初始化数据量的长度必须是5的整数倍！");
        }
        int num = 5;
        try {
            //计数器数量就等于线程数量
            CountDownLatch countDownLatch = new CountDownLatch(num);
            for (int i = 0; i < num; i++) {
                sysAccessLogService.executeAsyncInsertSysAccessLog(countDownLatch, i, size / num);
            }
            //主线程唤醒
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("end");
        }
        return AjaxResult.success();
    }

    @GetMapping("/referer")
    public AjaxResult referer(){
        return AjaxResult.success();
    }

    @GetMapping("/acccessLog/add")
    @RateLimit(key= "testLimit", count = 2, cycle = 1, msg = "访问过于频繁请稍后再试")
    public AjaxResult invoke(@RequestParam String requestUrl) throws Exception {

        switch (requestUrl) {
            case "0":
                requestUrl = "/home";
                break;
            case "1":
                requestUrl = "/cloudDesign";
                break;
            case "2":
                requestUrl = "/mxcrm";
                break;
            case "3":
                requestUrl="/mxrtgc";
                break;
            case "4":
                requestUrl="/mxerp";
                break;
            case "5":
                requestUrl="/ecErp";
                break;
            case "6":
                requestUrl="/brand";
                break;
            case "7":
                requestUrl="/mmjj";
                break;
            case "8":
                requestUrl="/chanjet";
                break;
            case "9":
                requestUrl="/cjthsy";
                break;
            case "10":
                requestUrl="/wms";
                break;
            case "11":
                requestUrl="/internet";
                break;
            case "12":
                requestUrl="/SupplyFinance";
                break;
            case "13":
                requestUrl="/mxfinance";
                break;
            case "14":
                requestUrl="/mxabout?active=0";
                break;
            default:
                return AjaxResult.success();
        }
        AsyncManager.me().execute(AsyncFactory.recordAccessLog(requestUrl, DateUtil.date()));
        return AjaxResult.success();
    }
}


