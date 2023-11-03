package com.base.test.common.service;

import cn.hutool.json.JSONObject;
import com.base.test.common.domain.model.WeatherInfo;
import com.base.test.common.domain.vo.WechatSendMsgVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeiXinService {

    public String getAccessToken(String appId, String appSecret);

    List<String> getUserList(String accessToken);

    //发送模板消息
    JSONObject sendMsg(WechatSendMsgVo sendMsgVo, String token, String openId);

    //彩虹屁信息
    String getCaiHongPiInfo(String appKey);

    //早安
    String getZaoAnInfo(String appKey);

    //天气信息
    WeatherInfo getWeatherInfo(String appKey, String city);
}
