package com.base.test.common.config;


import com.base.test.feignclient.UserFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lzs
 * @Date 2022/2/22 9:51
 **/
@Configuration
public class FeignConfig {

    @Value("${feign.url}")
    private String url;

    @Bean("customOkHttpClient")
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }



    @Bean
    public UserFeignClient identityFeignClient(@Qualifier("customOkHttpClient") OkHttpClient okHttpClient) {
        return Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).client(okHttpClient).target(UserFeignClient.class, url);
    }



}
