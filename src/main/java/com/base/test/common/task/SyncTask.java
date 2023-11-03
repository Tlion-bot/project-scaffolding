package com.base.test.common.task;

import com.base.test.project.business.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author nnc
 * @date 2023/11/1 9:33
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SyncTask {
    @Autowired
    UserServiceImpl userService;
    @Scheduled(fixedDelay = 60*60*1000)
    // @Scheduled(fixedDelay = 10*1000)
    public void run() {
        userService.synchronization();
    }
}
