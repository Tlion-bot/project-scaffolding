package com.base.test.project.montior.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.base.test.common.constant.Constants;
import com.base.test.common.controller.BaseController;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.core.page.TableDataInfo;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.common.utils.PageUtils;
import com.base.test.project.montior.domain.SysUserOnline;
import com.base.test.project.montior.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author baseWork
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController
{
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;


    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName)
    {
        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys)
        {
            LoginUser user = redisCache.getCacheObject(key);
            if (Validator.isNotEmpty(ipaddr) && Validator.isNotEmpty(userName))
            {
                if (StrUtil.equals(ipaddr, user.getIpaddr()) && StrUtil.equals(userName, user.getUsername()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            }
            else if (Validator.isNotEmpty(ipaddr))
            {
                if (StrUtil.equals(ipaddr, user.getIpaddr()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            }
            else if (Validator.isNotEmpty(userName) && Validator.isNotNull(user.getUser()))
            {
                if (StrUtil.equals(userName, user.getUsername()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            }
            else
            {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return PageUtils.buildDataInfo(userOnlineList);
    }

    /**
     * 强退用户
     */

    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId)
    {
        LoginUser object = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        redisCache.deleteObject(Constants.LOGIN_USERID_KEY+object.getUser().getUserId());
        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
