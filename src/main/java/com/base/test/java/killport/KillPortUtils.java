package com.base.test.java.killport;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName KillPortUtils
 * @Description KillPortUtils 关闭端口
 * @Author zhengjianhua
 * @Date 2021/8/20 11:06
 * @Version 1.0
 */
public class KillPortUtils {
    /** 日志 */
    private static final Log logger = LogFactory.getLog(KillPortUtils.class);

    // 开始方法
    public static void start(int port) throws IOException {
        Set<Integer> ports = new HashSet<>();
        // 将要关闭的端口添加到set中
        ports.add(port);
        // 判断linux环境
        Boolean isLinux = isOSLinux();
        // 查询端口命令 linux 与 windows区分
        String command = isLinux ? "netstat -tunlp |grep " + port : "cmd /c netstat -ano | findstr " + port;
        logger.error("执行命令:" + command);
        // 读取内容
        List<String> read = execAndRead(command, isLinux, ports);
        if (read.size() == 0) {
            logger.error("未查询到端口被占用");
            return;
        } else {
            // 关闭端口
            kill(read, isLinux);
        }
    }

    // 执行命令并且读取结果
    private static List<String> execAndRead(String command, Boolean isLinux, Set<Integer> ports) throws IOException {
        // 读取结果
        List<String> read = new ArrayList<String>();
        if (!isLinux) {
            PowerShell session = PowerShell.openSession();
            PowerShellResponse powerShellResponse = session.executeCommand(command);
            String outPut = powerShellResponse.getCommandOutput();
            if (StringUtils.isBlank(outPut)) {
                logger.error("未查询到端口被占用");
                session.close();
                return read;
            }
            // 获取换行符
            String lineSeparator = System.lineSeparator();
            // 换行
            String[] lineArray = outPut.split(lineSeparator);
            if (lineArray != null && lineArray.length > 0) {
                for (int i = 0; i < lineArray.length; i++) {
                    String line = lineArray[i];
                    // 验证端口
                    boolean validPort = validPort(line, isLinux, ports);
                    if (validPort) {
                        // 添加内容
                        read.add(line);
                    }
                }
            }
            session.close();
        } else {
            Runtime runtime = Runtime.getRuntime();
            //查找进程号
            Process p = runtime.exec(command);
            InputStream inputStream = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    // 验证端口
                    boolean validPort = validPort(line, isLinux, ports);
                    if (validPort) {
                        // 添加内容
                        read.add(line);
                    }
                }
                inputStream.close();
                reader.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                inputStream.close();
                reader.close();
            }
        }
        return read;
    }

    /**
     * 验证此行是否为指定的端口，因为 findstr命令会是把包含的找出来，例如查找80端口，但是会把8099查找出来
     *
     * @param str
     * @return
     */
    private static boolean validPort(String str, Boolean isLinux, Set<Integer> ports) {
        String find = "";
        // linux TCP    0.0.0.0:12349          0.0.0.0:0              LISTENING       30700
        // windows tcp        0      0 0.0.0.0:8888            0.0.0.0:*               LISTEN      2319/python
        String reg = isLinux ? ":[0-9]+" : "^ *[a-zA-Z]+ +\\S+";
        // 匹配正则
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        Boolean findFlag = matcher.find();
        logger.error("读取数据：" + str);
        // 未匹配到则直接返回
        if (!findFlag) {
            return false;
        }
        // 获取匹配内容
        find = matcher.group();
        // 处理数据
        int spstart = find.lastIndexOf(":");
        // 截取掉冒号
        find = find.substring(spstart + 1);
        int port = 0;
        try {
            port = Integer.parseInt(find);
        } catch (NumberFormatException e) {
            return false;
        }
        // 端口在其中 则通过验证
        return ports.contains(port);
    }

    /**
     * 更换为一个Set，去掉重复的pid值
     *
     * @param data
     */
    private static void kill(List<String> data, Boolean isLinux) throws IOException {
        // linux TCP    0.0.0.0:12349          0.0.0.0:0              LISTENING       30700
        // windows tcp        0      0 0.0.0.0:8888            0.0.0.0:*               LISTEN      2319/python
        Set<Integer> pids = new HashSet<>();
        for (String line : data) {
            // 去除前后空格
            line = line.trim();
            // 获取最后一个空格下标
            int offset = line.lastIndexOf(" ");
            // 截取最后的内容 如 30700 或者 2319/python
            String spid = line.substring(offset);
            // 替换其中的空格
            spid = spid.replaceAll(" ", "");
            // 如果存在/
            int lastSlashIndex = spid.lastIndexOf("/");
            if (lastSlashIndex != -1) {
                // 处理/
                spid = spid.substring(0, lastSlashIndex);
            }
            try {
                int pid = 0;
                pid = Integer.parseInt(spid);
                pids.add(pid);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.error("需要关闭的pid：" + pids);
        if (CollectionUtils.isNotEmpty(pids)) {
            killWithPid(pids, isLinux);
        }
    }

    /**
     * 一次性杀除所有的端口
     *
     * @param pids
     */
    private static void killWithPid(Set<Integer> pids, Boolean isLinux) throws IOException {
        if (isLinux) {
            for (Integer pid : pids) {
                String commond = "kill -9 " + pid;
                logger.error("关闭端口命令:" + commond);
                Process process = Runtime.getRuntime().exec(commond);
                InputStream inputStream = process.getInputStream();
                String txt = readTxt(inputStream, "GBK");
                logger.error("关闭端口结果:" + txt);
            }
        } else {
            PowerShell session = PowerShell.openSession();
            for (Integer pid : pids) {
                String commond = "taskkill /F /pid " + pid;
                logger.error("关闭端口命令:" + commond);
                PowerShellResponse powerShellResponse = session.executeCommand(commond);
                String outPut = powerShellResponse.getCommandOutput();
                logger.error("关闭端口结果:" + outPut);
            }
            session.close();
        }
    }


    private static List<String> read(String outPut, Boolean isLinux, Set<Integer> ports) throws IOException {
        List<String> data = new ArrayList<>();
        // 获取换行符
        String lineSeparator = System.lineSeparator();
        // 换行
        String[] lineArray = outPut.split(lineSeparator);
        if (lineArray != null && lineArray.length > 0) {
            for (int i = 0; i < lineArray.length; i++) {
                String line = lineArray[i];
                // 验证端口
                boolean validPort = validPort(line, isLinux, ports);
                if (validPort) {
                    // 添加内容
                    data.add(line);
                }
            }
        }
        return data;
    }


    private static boolean isOSLinux() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        return os != null && os.toLowerCase().indexOf("linux") > -1;
    }

    private static String readTxt(InputStream in, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

}