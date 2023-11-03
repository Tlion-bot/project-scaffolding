package com.base.test.common.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.base.test.common.constant.UrlConstant;
import com.base.test.common.domain.model.WeatherInfo;
import com.base.test.common.domain.vo.WechatSendMsgVo;
import com.base.test.common.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WeiXinServiceImpl implements WeiXinService {
    @Override
    public String getAccessToken(String appId, String appSecret) {
        String requestUrl = UrlConstant.ACCESS_TOKEN_URL + "appid=" + appId + "&secret=" + appSecret;
        String resp = HttpUtil.get(requestUrl);
        JSONObject result = JSONUtil.parseObj(resp);
        log.info("获取access_token:" + resp);
        String token = result.getStr("access_token");
        log.info("token:" + token);
        return token;

    }
    @Override
    public List<String> getUserList(String accessToken) {
        String requestUrl =  UrlConstant.GET_USER_LIST+ accessToken;
        String resp = HttpUtil.get(requestUrl);
        JSONObject result = JSONUtil.parseObj(resp);
        log.info("用户列表:" + resp);
        JSONArray openIdJsonArray = result.getJSONObject("data").getJSONArray("openid");
        List<String> openIds = JSONUtil.toList(openIdJsonArray, String.class);
        return openIds;
    }
    @Override
    public JSONObject sendMsg(WechatSendMsgVo sendMsgVo, String token, String openId) {
        //请求路径
        String requestUrl = UrlConstant.SEND_TEMPLATE  + token;
        //参数转json
        String json = JSONUtil.parseObj(sendMsgVo).toString();
        String resp = HttpUtil.createPost(requestUrl).body(json).execute().body();
        JSONObject result = JSONUtil.parseObj(resp);
        log.info("发送消息:" + resp);
        return result;
    }

    @Override
    public String getCaiHongPiInfo(String appKey) {
        //请求地址
        String requestUrl = UrlConstant.CAI_HONG_API + appKey;
        String resp = HttpUtil.createGet(requestUrl).contentType("application/x-www-form-urlencoded;charset=UTF-8").charset("UTF-8").execute().body();
        JSONObject obj = JSONUtil.parseObj(resp);
        log.info("obj"+obj.toString());
        JSONArray newslist = obj.getJSONArray("newslist");
        String content = ((JSONObject) newslist.get(0)).getStr("content");
        return content;
    }

    @Override
    public String getZaoAnInfo(String appKey) {
        //请求地址
        String requestUrl = UrlConstant.MORNING_API + appKey;
        String resp = HttpUtil.createGet(requestUrl).contentType("application/x-www-form-urlencoded;charset=UTF-8").charset("UTF-8").execute().body();
        JSONObject obj = JSONUtil.parseObj(resp);
        log.info("obj"+obj.toString());
        JSONArray newslist = obj.getJSONArray("newslist");
        String content = ((JSONObject) newslist.get(0)).getStr("content");
        return content;
    }

    @Override
    public WeatherInfo getWeatherInfo(String appKey, String city) {
        //请求地址
        String requestUrl = UrlConstant.TIAN_QI_API + appKey + "&city=" + city;
        String resp = HttpUtil.createGet(requestUrl).contentType("application/x-www-form-urlencoded;charset=UTF-8").charset("UTF-8").execute().body();
        JSONObject obj = JSONUtil.parseObj(resp);
        log.info("obj"+obj.toString());
        JSONArray newslist = obj.getJSONArray("newslist");
        List<WeatherInfo> weatherInfos = JSONUtil.toList(newslist, WeatherInfo.class);
        return weatherInfos.get(0);
    }


}
