package com.base.test.common.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author baseWork
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "base-work")
public class BaseWorkConfig
{
    /** 项目名称 */
	@Getter
    private static String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    @Getter
    private static String profile;

    /** 获取地址开关 */
    @Getter
    private static boolean addressEnabled;

    public void setProfile(String profile)
    {
        BaseWorkConfig.profile = profile;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        BaseWorkConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
