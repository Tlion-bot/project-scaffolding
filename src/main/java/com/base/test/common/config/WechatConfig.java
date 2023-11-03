package com.base.test.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
    private String appId;
    private String appSecret;
    private String tempId;
    private String myBirthday;
    private String babyBirthday;
    private String loveDay;

}
