package com.base.test;

import cn.hutool.json.JSONObject;
import com.base.test.common.config.TianApiConfig;
import com.base.test.common.config.WechatConfig;
import com.base.test.common.domain.model.WeatherInfo;
import com.base.test.common.domain.vo.WechatSendMsgVo;
import com.base.test.common.domain.vo.WechatTemplateVo;
import com.base.test.common.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@ContextConfiguration
@SpringBootTest(classes = TestApplication.class)
class TemplateApplicationTests {


    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private TianApiConfig tianApiConfig;

    @Test
    void sendMsg() throws ParseException {
        //配置及数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String appId = wechatConfig.getAppId();
        String appSecret = wechatConfig.getAppSecret();
        LocalDate babyBirthday = LocalDate.parse(wechatConfig.getBabyBirthday());
        LocalDate myBirthday = LocalDate.parse(wechatConfig.getMyBirthday());
        LocalDate loveDay = LocalDate.parse(wechatConfig.getLoveDay());
        String appKey = tianApiConfig.getAppKey();
        String area = tianApiConfig.getArea();
        //获取微信token
        String token = weiXinService.getAccessToken(appId,appSecret);
        //获取关注用户
        List<String> userList = weiXinService.getUserList(token);
        for (String openId : userList) {
            //发送消息实体
            WechatSendMsgVo sendMsgVo = new WechatSendMsgVo();
            //设置模板id
            sendMsgVo.setTemplate_id(wechatConfig.getTempId());
            //设置接收用户
            sendMsgVo.setTouser(openId);
            Map<String, WechatTemplateVo> map = new HashMap<>();
            //获取早安语句
            String zaoAnInfo = weiXinService.getZaoAnInfo(appKey);
            map.put("morning", new WechatTemplateVo("Baby 早安！"+zaoAnInfo,"#ff6666"));
            //获取天气
            WeatherInfo weatherInfo = weiXinService.getWeatherInfo(appKey, area);
            //日期
            map.put("date", new WechatTemplateVo(weatherInfo.getDate(),null));
            //星期
            map.put("week",new WechatTemplateVo(weatherInfo.getWeek(),null));
            //城市
            map.put("city",new WechatTemplateVo(weatherInfo.getArea(),"#9900ff"));
            //天气
            map.put("weather",new WechatTemplateVo(weatherInfo.getWeather(),"#CD96CD"));
            //最低气温
            map.put("lowest",new WechatTemplateVo(weatherInfo.getLowest(),"#A4D3EE"));
            //最高气温
            map.put("highest",new WechatTemplateVo(weatherInfo.getHighest(),"#CD3333"));
            //降水量
            map.put("pcpn",new WechatTemplateVo(weatherInfo.getPcpn()+"%","#A4D3EE"));
            //今日建议
            map.put("tips",new WechatTemplateVo(weatherInfo.getTips(),"#FF7F24"));
            //相爱天数
            long loveDays = fun2(loveDay);
            map.put("loveDay",new WechatTemplateVo(loveDays+"","#EE6AA7"));
            //我的生日
            long myDay = fun2(myBirthday);
            map.put("myBirthday",new WechatTemplateVo(myDay+"","#EE6AA7"));
            //宝贝生日
            long babyDay = fun2(babyBirthday);
            map.put("babyBirthday",new WechatTemplateVo(babyDay+"","#EE6AA7"));
            //彩虹屁
            String caiHongPiInfo = weiXinService.getCaiHongPiInfo(appKey);
            map.put("pipi",new WechatTemplateVo(caiHongPiInfo,"#E066FF"));

            sendMsgVo.setData(map);
            JSONObject entries = weiXinService.sendMsg(sendMsgVo,token, openId);
        }
    }

    // @Test
    // void sendDrinkMsg(){
    //     //配置及数据
    //     String appId = wechatConfig.getAppId();
    //     String appSecret = wechatConfig.getAppSecret();
    //     String appKey = tianApiConfig.getAppKey();
    //     //获取微信token
    //     String token = weiXinService.getAccessToken(appId,appSecret);
    //     //获取关注用户
    //     List<String> userList = weiXinService.getUserList(token);
    //     for (String openId : userList) {
    //         //发送消息实体
    //         WechatSendMsgVo sendMsgVo = new WechatSendMsgVo();
    //         //设置喝水模板id
    //         sendMsgVo.setTemplate_id(wechatConfig.getDrinkTempId());
    //         //设置接收用户
    //         sendMsgVo.setTouser(openId);
    //         Map<String, WechatTemplateVo> map = new HashMap<>();
    //         //获取笑话
    //         String joke = weiXinService.getJoke(appKey);
    //         map.put("joke", new WechatTemplateVo(joke,"#ff6666"));
    //         sendMsgVo.setData(map);
    //         JSONObject entries = weiXinService.sendMsg(sendMsgVo,token, openId);
    //     }
    // }

    // @Test
    // void sendoffDutyMsg(){
    //     String content = "卸载上班的压力，删除上班的烦恼，设置明天的斗志,下载轻松的话题，安装快乐的心情，播放灿烂的笑容。";
    //     //配置及数据
    //     String appId = wechatConfig.getAppId();
    //     String appSecret = wechatConfig.getAppSecret();
    //     String appKey = tianApiConfig.getAppKey();
    //     //获取微信token
    //     String token = weiXinService.getAccessToken(appId,appSecret);
    //     //获取关注用户
    //     List<String> userList = weiXinService.getUserList(token);
    //     for (String openId : userList) {
    //         //发送消息实体
    //         WechatSendMsgVo sendMsgVo = new WechatSendMsgVo();
    //         //设置喝水模板id
    //         sendMsgVo.setTemplate_id(wechatConfig.getOffDutyTempId());
    //         //设置接收用户
    //         sendMsgVo.setTouser(openId);
    //         Map<String, WechatTemplateVo> map = new HashMap<>();
    //         //获取笑话
    //         String sayLove = weiXinService.getSayLove(appKey);
    //         map.put("tips",new WechatTemplateVo(content,"#ff6666"));
    //         map.put("sayLove", new WechatTemplateVo(sayLove,"#E066FF"));
    //         sendMsgVo.setData(map);
    //         JSONObject entries = weiXinService.sendMsg(sendMsgVo,token, openId);
    //     }
    // }



    @Test
    void test() throws ParseException {
       LocalDate birthDate=LocalDate.of(2001,07,9);
       long i=fun2(birthDate);
        log.info(String.valueOf(i));
    }


    public long fun(LocalDate s1,LocalDate s2) throws  ParseException {
        // 获取当前日期


        // 计算下个生日的日期
        LocalDate nextBirthday = s1.withYear(s2.getYear());

        // 如果生日已经过了，就计算下一年的生日
        if (nextBirthday.isBefore(s2) || nextBirthday.isEqual(s2)) {
            nextBirthday = nextBirthday.plusYears(1).withMonth(s1.getMonthValue()).withDayOfMonth(s1.getDayOfMonth());
        }

        // 计算离下个生日还有多少天
        long daysUntilNextBirthday = ChronoUnit.DAYS.between(s2, nextBirthday);
        return daysUntilNextBirthday;
    }

    public long fun2(LocalDate birthDate) throws  ParseException {

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算下个生日的日期
        LocalDate nextBirthday = birthDate.withYear(currentDate.getYear());

        // 如果生日已经过了，就计算下一年的生日
        if (nextBirthday.isBefore(currentDate) || nextBirthday.isEqual(currentDate)) {
            nextBirthday = nextBirthday.plusYears(1).withMonth(birthDate.getMonthValue()).withDayOfMonth(birthDate.getDayOfMonth());
        }

        // 计算离下个生日还有多少天
        long daysUntilNextBirthday = ChronoUnit.DAYS.between(currentDate, nextBirthday);
        return daysUntilNextBirthday;
    }


}
