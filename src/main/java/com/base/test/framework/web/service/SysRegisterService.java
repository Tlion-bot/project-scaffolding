package com.base.test.framework.web.service;


import cn.hutool.core.bean.BeanUtil;
import com.base.test.common.constant.Constants;
import com.base.test.common.constant.UserConstants;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.domain.model.RegisterBody;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.common.utils.SecurityUtils;
import com.base.test.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class SysRegisterService {
    @Autowired
    private ISysUserService userService;


    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AsyncService asyncService;
    String msg1 = "";

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {

        String msg = "";
        SysUser sysUser = BeanUtil.copyProperties(registerBody, SysUser.class);
        String code = registerBody.getCode();
        String username = sysUser.getUserName();
        String uuid = registerBody.getUuid();
        String password = sysUser.getPassword();

        if (StringUtils.isEmpty(sysUser.getUserName())) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(sysUser.getPassword())) {
            msg = "用户密码不能为空";
        } else if (sysUser.getUserName().length() < UserConstants.USERNAME_MIN_LENGTH || sysUser.getUserName().length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (sysUser.getNickName().length() < UserConstants.NICKNAME_MIN_LENGTH || sysUser.getNickName().length() > UserConstants.NICKNAME_MAX_LENGTH) {
            msg = "昵称长度必须在1到20个字符之间";
        } else if (sysUser.getPassword().length() < UserConstants.PASSWORD_MIN_LENGTH || sysUser.getPassword().length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            msg = "保存用户'" + sysUser.getUserName() + "'失败，注册账号已存在";
        } else if (!checkPhone(sysUser.getPhonenumber())) {

            msg = "手机号格式不正确";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(sysUser))) {
            msg = "保存用户'" + sysUser.getUserName() + "'失败，手机号码已存在";
        } else if (!validateCaptcha(username, code, uuid)) {
            msg = msg1;
        } else {
            sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public boolean validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (org.apache.commons.lang3.StringUtils.isBlank(code)) {

            msg1 = "验证码错误";
            return false;

        } else if (captcha == null) {
            msg1 = "验证码已失效";
            return false;
        } else if (!code.equalsIgnoreCase(captcha)) {
            msg1 = "验证码错误";
            return false;
        } else {
            return true;
        }
    }


    /**
     * 判断手机号格式是否正确
     *
     * @author nnc
     * @date 2023/3/23 14:46
     */
    public static boolean checkPhone(String phone) {

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (StringUtils.isEmpty(phone)) {
            return false;
        } else {
            if (phone.length() != 11) {
                return false;
            } else {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(phone);
                boolean isMatch = m.matches();
                if (isMatch) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}