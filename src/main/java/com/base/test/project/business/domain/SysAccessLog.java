package com.base.test.project.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author nnc
 * @Date 2023/8/19 13:54
 * @Description 访问日志
 **/
@Data
@TableName("sys_access_log")
@Accessors(chain = true)
public class SysAccessLog implements Serializable {
    private static final long serialVersionUID = 1L;
    //id
    @TableId(type = IdType.AUTO)
    private Long id;

    private String ipaddr;

    private String browser;

    private String os;

    private String requestUrl;

    private String requestMethod;

    private Date requestTime;

    public SysAccessLog(String ipaddr, String browser, String os, String requestUrl, String requestMethod, Date requestTime){
        this.ipaddr = ipaddr;
        this.browser = browser;
        this.os = os;
        this.requestMethod = requestMethod;
        this.requestUrl= requestUrl;
        this.requestTime = requestTime;
    }

}
