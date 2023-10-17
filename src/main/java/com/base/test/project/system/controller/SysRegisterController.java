package com.base.test.project.system.controller;

import com.base.test.common.controller.BaseController;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.core.domain.model.RegisterBody;
import com.base.test.framework.web.service.SysRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.base.test.common.core.domain.AjaxResult.error;
import static com.base.test.common.core.domain.AjaxResult.success;

/**
 * 注册验证
 *
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;



    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        // if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        // {
        //     return error("当前系统没有开启注册功能！");
        // }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}