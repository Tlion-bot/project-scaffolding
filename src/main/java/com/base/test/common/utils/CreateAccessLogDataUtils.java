package com.base.test.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author chenqingshan
 * @Date 2023/7/5 14:06
 * @Description TODO
 **/
public class CreateAccessLogDataUtils {
    /**
     * 随机生成浏览器
     * @return
     */
    public synchronized static String getRandomUrl(){

        String[] range = {
                "https://jxmx.jxbashen.com/#/home",
                "https://jxmx.jxbashen.com/#/cloudDesign",
                "https://jxmx.jxbashen.com/#/mxcrm",
                "https://jxmx.jxbashen.com/#/mxrtgc",
                "https://jxmx.jxbashen.com/#/mxerp",
                "https://jxmx.jxbashen.com/#/ecErp",
                "https://jxmx.jxbashen.com/#/brand",
                "https://jxmx.jxbashen.com/#/mmjj",
                "https://jxmx.jxbashen.com/#/chanjet",
                "https://jxmx.jxbashen.com/#/cjthsy",
                "https://jxmx.jxbashen.com/#/wms",
                "https://jxmx.jxbashen.com/#/internet",
                "https://jxmx.jxbashen.com/#/SupplyFinance",
                "https://jxmx.jxbashen.com/#/mxabout?active=0",
                "https://jxmx.jxbashen.com/#/apply",
                "https://jxmx.jxbashen.com/#/mxlogin",
                "https://jxmx.jxbashen.com/#/mxregister",
                "https://jxmx.jxbashen.com/#/experience?type=erp",
                "https://jxmx.jxbashen.com/#/experience?type=CloudDesign",
                "https://jxmx.jxbashen.com/#/experience?type=rtgc",
                "https://jxmx.jxbashen.com/#/experience?type=ppzq",
                "https://jxmx.jxbashen.com/#/experience?type=crm",
                "https://jxmx.jxbashen.com/#/experience?type=wms",
                "https://jxmx.jxbashen.com/#/experience?type=hsy",
                "https://jxmx.jxbashen.com/#/experience?type=chanjet"
        };
        Random random = new Random();
        int index = random.nextInt(range.length);
        String url = range[index];
        return url;
    }

    /**
     * 随机生成浏览器
     * @return
     */
    public synchronized static String getRandomMethod(){

        String[] range = {"GET","POST"};
        Random random = new Random();
        int index = random.nextInt(2);
        String browser = range[index];
        return browser;
    }
    /**
     * 随机生成浏览器
     * @return
     */
    public synchronized static String getRandomBrowser(){

        String[] range = {"Chrome 8","Chrome 9","Internet Explorer 11","Internet Explorer 10","Chrome 11","Chrome 10","Firefox 11","Safari"};
        Random random = new Random();
        int index = random.nextInt(8);
        String browser = range[index];
        return browser;
    }

    /**
     * 随机生成操作系统
     * @return
     */
    public synchronized static String getRandomOS(){

        String[] range = {"Windows 10","Windows 7","Mac OS X","Windows 11","Linux-gnu"};
        Random random = new Random();
        int index = random.nextInt(5);
        String browser = range[index];
        return browser;
    }
    /**
     * 获取一个随机IP
     */
    public synchronized static String getRandomIp() {

        // 指定 IP 范围
        int[][] range = {
                {607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random random = new Random();
        int index = random.nextInt(10);
        String ip = num2ip(range[index][0] + random.nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成IP地址
     */
    public synchronized static String num2ip(int ip) {
        int[] b = new int[4];
        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        // 拼接 IP
        String x = b[0] + "." + b[1] + "." + b[2] + "." + b[3];
        return x;
    }

    /**
     * 生成size数量的随机时间，位于[start,end)范围内 时间倒序排列
     * @param start 开始时间
     * @param end 结束时间
     * @return List<Date>
     * @throws Exception
     */
    public  synchronized static Date randomDate(String start, String end) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(start);
        Date endTime = sdf.parse(end);

        Random random = new Random();
        List<Date> dates = random.longs(1, startTime.getTime(), endTime.getTime()).mapToObj(t -> new Date(t)).collect(Collectors.toList());
        return dates.get(0);
    }

}
