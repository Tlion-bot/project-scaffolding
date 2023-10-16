package com.base.test.common.task;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.domain.UserCopy;
import com.base.test.project.business.service.UserCopyService;
import com.base.test.project.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author lzs
 * @Date 2022/6/10 11:16
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class SyncTask {

    private final UserService userService;
    private final UserCopyService userCopyService;

    @Scheduled(fixedDelay = 60*60*1000)
    public void run() {
        log.info("-------------定时任务：用户数据，开始-----------");
        try {
            Page<User> pageList = userService.pageList();
            for (User user : pageList.getRecords()) {
                //覆盖另一张表

                if (userCopyService.lambdaQuery().eq(UserCopy::getName, user.getName()).count()<=0){


                    UserCopy userCopy=BeanUtil.copyProperties(user,UserCopy.class);
                    userCopyService.save(userCopy);
                }

            }
            log.info("-------------定时任务：用户数据，结束-----------");
        } catch (Exception e) {
            log.error("定时任务：同步租户或用户数据，出现错误", e);
        }
    }
}
