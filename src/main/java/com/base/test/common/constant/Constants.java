package com.base.test.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "rtgc_login_user_key";

    public static final String TV_LOGIN_USER_KEY = "rtgc_tv_login_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

	/**
	 * RMI 远程方法调用
	 */
	public static final String LOOKUP_RMI = "rmi://";

	/**
	 * 资源映射路径 前缀
	 */
	public static final String REDIS_LOCK_KEY = "redis_lock:";

    /**
     * 0
     */
    public static final String NO = "0";

    /**
     * 1
     */
    public static final String YSE = "1";

    /**
     * 链接携带参数
     */
    public static final String TV_TOKEN = "token";

    public static final int TV_TIME = 60;

    public static final int TV_TIME_OUT = 7 * 24 * 60 * 1000 *1000; //tv token有效期7天

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "123456";

    /**
     * 水印
     */
    public static final String  WATERMARK_CODE = "?x-image-process=style/style-50d5";

    /**
     * 手机号码正则表达式
     */
    public static final String PHONE_PATTERN = "^1[0-9]{10}$";
    /**
     * 密码正则表达式
     */
    public static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,20}$";
    /**
     * 手机号码错误提示信息
     */
    public static final String PHONE_ERROR_MSG = "手机号码不合法，手机号码要满足11位且以 13或14或15或17或18或19开头";
    /**
     * 密码错误提示信息
     */
    public static final String PASSWORD_ERROR_MSG = "密码要满足数字字母组合，长度8-20位";
    /**
     * 请求微信获取access_token
     */
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * 推送订阅
     */
    public static final String WX_PUSH_SUBSCRIBE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
    /**
     * 库存预警推送模板
     * */
    public static final String WX_PURCHASE_STOCK_WARN_TEMPLATE = "bp04jKdoCdDY7PAtvWbGpF_L6XhSYhnh6zXD8f1enBs";
}
