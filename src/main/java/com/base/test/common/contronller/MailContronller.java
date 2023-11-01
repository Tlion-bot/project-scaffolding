package com.base.test.common.contronller;

import cn.hutool.core.util.RandomUtil;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.enums.RedisKey;
import com.base.test.common.exception.CustomException;
import com.base.test.common.service.impl.EmailServiceImpl;
import com.base.test.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.base.test.common.constant.Constants.EMAIL_EXPIRED_TIME;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
public class MailContronller {

    @Autowired
    EmailServiceImpl emailService;


    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @PostMapping("/code")
    public AjaxResult<Void> sendMessageToEmail(@RequestParam("email") String email) {
        if (StringUtils.isAnyBlank(email)) {
            throw new CustomException("邮箱为空");
        }
        // 校验邮箱

        // 从redis中查看有没有该邮箱的验证码
        String verifyCode = String.valueOf(redisTemplate.opsForValue().get(RedisKey.EMAIL_CODE.getCode() + email));
        if (!verifyCode.equals("null")) {
            throw new CustomException( "验证码已发送=>" + verifyCode);
        }
        // 如果redis没有该手机号验证码，则获取验证码并发送短信
        verifyCode = RandomUtil.randomString(6); // 获取六位验证码
        emailService.sendMessageToEmail(verifyCode, email);
        // 将该验证码存入redis
        redisTemplate.opsForValue().set(
                RedisKey.EMAIL_CODE.getCode() + email,
                verifyCode,
                EMAIL_EXPIRED_TIME,
                TimeUnit.MINUTES);
        return AjaxResult.success("发送成功");
    }

}
