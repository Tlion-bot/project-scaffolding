package com.base.test.common.contronller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序消息推送
 */
@Slf4j
@RestController
@RequestMapping("wx/ma/welcome")
public class WxMaMsgController {

    @Autowired
    private WxMaService wxMaService;

    /**
     * 消息校验，确定是微信发送的消息
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws Exception
     */
    @GetMapping
    public String doGet(String signature, String timestamp, String nonce, String echostr) {
        // 消息合法
        if (wxMaService.checkSignature(timestamp, nonce, signature)) {
            log.info("-------------微信小程序消息验证通过");
            return echostr;
        }
        // 消息签名不正确，说明不是公众平台发过来的消息
        return null;
    }
}
