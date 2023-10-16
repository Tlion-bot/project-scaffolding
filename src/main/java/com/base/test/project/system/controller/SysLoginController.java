package com.base.test.project.system.controller;

import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.domain.model.LoginBody;
import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.utils.ServletUtils;
import com.base.test.framework.web.service.SysLoginService;
import com.base.test.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证
 *
 * @author baseWork
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    // @Autowired
    // private ISysMenuService menuService;
    //
    // @Autowired
    // private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody, @RequestHeader Integer client) {
        loginBody.setClient(client);
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody);
        ajax.put(Constants.TOKEN, token);
        return AjaxResult.success(ajax);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        // Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        // Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        // ajax.put("roles", roles);
        // ajax.put("permissions", permissions);
        return AjaxResult.success(ajax);
    }

    // /**
    //  * 获取路由信息
    //  *
    //  * @return 路由信息
    //  */
    // @GetMapping("getRouters")
    // public AjaxResult getRouters() {
    //     LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    //     // 用户信息
    //     SysUser user = loginUser.getUser();
    //     List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
    //     return AjaxResult.success(menuService.buildMenus(menus));
    // }
}
