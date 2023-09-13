package com.base.test.common.manager.factory;

import com.base.test.common.utils.LogUtils;
import com.base.test.common.utils.ServletUtils;
import com.base.test.common.utils.SpringUtils;
import com.base.test.common.utils.ip.AddressUtils;
import com.base.test.common.utils.ip.IpUtils;
import com.base.test.project.business.domain.SysAccessLog;
import com.base.test.project.business.service.impl.SysAccessLogServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    // /**
    //  * 记录登陆信息
    //  *
    //  * @param username 用户名
    //  * @param status   状态
    //  * @param message  消息
    //  * @param args     列表
    //  * @return 任务task
    //  */
    // public static TimerTask recordLogininfor(final Long userId, final String username, final String status,
    // 		final String message, final Object... args) {
    // 	final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    // 	final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    // 	return new TimerTask() {
    // 		@Override
    // 		public void run() {
    // 			String address = AddressUtils.getRealAddressByIP(ip);
    // 			StringBuilder s = new StringBuilder();
    // 			s.append(LogUtils.getBlock(ip));
    // 			s.append(address);
    // 			s.append(LogUtils.getBlock(username));
    // 			s.append(LogUtils.getBlock(status));
    // 			s.append(LogUtils.getBlock(message));
    // 			// 打印信息到日志
    // 			sys_user_logger.info(s.toString(), args);
    // 			// 获取客户端操作系统
    // 			String os = userAgent.getOperatingSystem().getName();
    // 			// 获取客户端浏览器
    // 			String browser = userAgent.getBrowser().getName();
    // 			// 封装对象
    // 			SysLoginInfo logininfor = new SysLoginInfo();
    // 			logininfor.setUserName(username);
    // 			logininfor.setIpaddr(ip);
    // 			logininfor.setLoginLocation(address);
    // 			logininfor.setBrowser(browser);
    // 			logininfor.setOs(os);
    // 			logininfor.setMsg(message);
    // 			// 日志状态
    // 			if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
    // 				logininfor.setStatus(Constants.SUCCESS);
    // 			} else if (Constants.LOGIN_FAIL.equals(status)) {
    // 				logininfor.setStatus(Constants.FAIL);
    // 			}
    // 			Date date = new Date();
    // 			logininfor.setLoginTime(date);
    // 			// 插入数据
    // 			SpringUtils.getBean(SysLoginInfoService.class).save(logininfor);
    //
    // 			SysUser ouser = new SysUser();
    // 			ouser.setUserId(userId);
    // 			ouser.setLoginDate(date);
    // 			ouser.setUpdateTime(date);
    // 			SpringUtils.getBean(SysUserService.class).updateById(ouser);
    // 		}
    // 	};
    // }

    // public static TimerTask recordInvokeRequest(final Long id, final String method, final String urlType, final String requestUrl, final String sendData) {
    // 	return new TimerTask() {
    // 		@Override
    // 		public void run() {
    // 			// 封装对象
    // 			SysInvokeLog invokeLog = new SysInvokeLog();
    // 			invokeLog.setId(id);
    // 			invokeLog.setUrlType(urlType);
    // 			invokeLog.setRequestUrl(requestUrl);
    // 			invokeLog.setSendData(sendData);
    // 			invokeLog.setCreateTime(new Date());
    // 			// 保存数据
    // 			SpringUtils.getBean(SysInvokeLogService.class).save(invokeLog);
    // 		}
    // 	};
    // }
    //
    // public static TimerTask recordInvokeResponse(final Long id, final String code, final String returnData) {
    // 	return new TimerTask() {
    // 		@Override
    // 		public void run() {
    // 			// 封装对象
    // 			SysInvokeLog invokeLog = new SysInvokeLog();
    // 			invokeLog.setId(id);
    // 			invokeLog.setReturnData(returnData);
    // 			invokeLog.setCode(code);
    // 			invokeLog.setUpdateTime(new Date());
    // 			// 更新数据
    // 			SpringUtils.getBean(SysInvokeLogService.class).updateById(invokeLog);
    // 		}
    // 	};
    // }
    // /**
    //  * 记录登陆信息
    //  *
    //  * @param username 用户名
    //  * @param status   状态
    //  * @param message  消息
    //  * @param args     列表
    //  * @return 任务task
    //  */
    // public static TimerTask recordLogininfor(final String username, final String status, final String message,
    // 										 final Object... args) {
    // 	final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    // 	final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    // 	return new TimerTask() {
    // 		@Override
    // 		public void run() {
    // 			String address = AddressUtils.getRealAddressByIP(ip);
    // 			StringBuilder s = new StringBuilder();
    // 			s.append(LogUtils.getBlock(ip));
    // 			s.append(address);
    // 			s.append(LogUtils.getBlock(username));
    // 			s.append(LogUtils.getBlock(status));
    // 			s.append(LogUtils.getBlock(message));
    // 			// 打印信息到日志
    // 			sys_user_logger.info(s.toString(), args);
    // 			// 获取客户端操作系统
    // 			String os = userAgent.getOperatingSystem().getName();
    // 			// 获取客户端浏览器
    // 			String browser = userAgent.getBrowser().getName();
    // 			// 封装对象
    // 			SysLogininfor logininfor = new SysLogininfor();
    // 			logininfor.setUserName(username);
    // 			logininfor.setIpaddr(ip);
    // 			logininfor.setLoginLocation(address);
    // 			logininfor.setBrowser(browser);
    // 			logininfor.setOs(os);
    // 			logininfor.setMsg(message);
    // 			// 日志状态
    // 			if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
    // 				logininfor.setStatus(Constants.SUCCESS);
    // 			} else if (Constants.LOGIN_FAIL.equals(status)) {
    // 				logininfor.setStatus(Constants.FAIL);
    // 			}
    // 			// 插入数据
    // 			SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
    // 		}
    // 	};

    /**
     * 访问记录
     *
     * @return 任务task
     */
    public static TimerTask recordAccessLog(final String requestUrlafferent, final Date date) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        final String requestUrl = "https://test.com/#" + requestUrlafferent;
        final String request = ServletUtils.getRequest().getMethod();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();

                // 封装对象
                SysAccessLog sysAccessLog = new SysAccessLog(ip, browser, os, requestUrl, request, date);
                // 日志状态
                // 插入数据
                SpringUtils.getBean(SysAccessLogServiceImpl.class).insertSysAccessLog(sysAccessLog);
            }
        };
    }

    public static TimerTask referer() {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());

        final HttpServletRequest request = ServletUtils.getRequest();
        final String requestReferer = request.getHeader("Referer");
        final String host = request.getServerName();
        final String requestHost = request.getHeader("host");
        final String requestMethod = ServletUtils.getRequest().getMethod();


        return new TimerTask() {
            @Override
            public void run() {
                java.net.URL url = null;
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                try {
                    url = new java.net.URL(requestReferer);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // // 获取客户端操作系统
                // String os = userAgent.getOperatingSystem().getName();
                // // 获取客户端浏览器
                // String browser = userAgent.getBrowser().getName();

                System.out.println(request);
                System.out.println(requestMethod);
                System.out.println(requestReferer);
                System.out.println(host);
                System.out.println(requestHost);
                System.out.println(url.getHost());
                System.out.println(url.getProtocol());

                // 封装对象

                // 日志状态
                // 插入数据

            }
        };
    }
}
