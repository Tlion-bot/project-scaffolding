package com.base.test.common.task;

import cn.hutool.json.JSONObject;
import com.base.test.common.config.TianApiConfig;
import com.base.test.common.config.WechatConfig;
import com.base.test.common.domain.model.WeatherInfo;
import com.base.test.common.domain.vo.WechatSendMsgVo;
import com.base.test.common.domain.vo.WechatTemplateVo;
import com.base.test.common.service.WeiXinService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信定时任务
 */
@Configuration
@EnableScheduling
public class GoodMorningTask {

    @Resource
    private WeiXinService weiXinService;

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private TianApiConfig tianApiConfig;

    /**
     * 微信模板消息推送
     * @throws ParseException
     */
    @Scheduled(cron="0 0 8 * * ? ")
    public void sendTemplateMsg() throws ParseException {
        //配置及数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
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
            //降水概率
            map.put("pop",new WechatTemplateVo(weatherInfo.getPop()+"%","#A4D3EE"));
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

    // @Scheduled(cron = "0 0 9-18 * * ? *")
    // public void sendDrinkMsg() {
    //     //配置及数据
    //     String appId = wechatConfig.getAppId();
    //     String appSecret = wechatConfig.getAppSecret();
    //     String appKey = tianApiConfig.getAppKey();
    //     //获取微信token
    //     String token = weiXinService.getAccessToken(appId, appSecret);
    //     //获取关注用户
    //     List<String> userList = weiXinService.getUserList(token);
    //     for (String openId : userList) {
    //         //发送消息实体
    //         WechatSendMsgVo sendMsgVo = new WechatSendMsgVo();
    //         //设置喝水模板id
    //         sendMsgVo.setTemplateId(wechatConfig.getDrinkTempId());
    //         //设置接收用户
    //         sendMsgVo.setTouser(openId);
    //         Map<String, WechatTemplateVo> map = new HashMap<>();
    //         //获取笑话
    //         String joke = weiXinService.getJoke(appKey);
    //         map.put("joke", new WechatTemplateVo(joke, "#ff6666"));
    //         sendMsgVo.setData(map);
    //         JSONObject entries = weiXinService.sendMsg(sendMsgVo, token, openId);
    //     }
    // }

    // @Scheduled(cron = "0 40 17 * * ? ")
    // public void sendoffDutyMsg(){
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

    public int fun(String s1,String s2) throws ParseException {
        //指定格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取Date
        Date t1 = simpleDateFormat.parse(s1);
        Date t2 = simpleDateFormat.parse(s2);

        //获取时间戳
        Long time1 = t1.getTime();
        Long time2 = t2.getTime();
        Long num = time2- time1;
        Long day= num/24/60/60/1000;
        //返回相差天数
        return day.intValue();
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
