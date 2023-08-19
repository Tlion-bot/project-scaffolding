package com.base.test.common.controller;

import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * web层通用数据处理
 * 
 * @author
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    
    public String getHeaderValue(HttpServletRequest request, String name) {
    	return request.getHeader(name);
    }
    
    public String getChannel(HttpServletRequest request) {
    	return getHeaderValue(request, Constants.HEADE);
    }
    
    
    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
    
    /**
     * 响应返回结果
     * 
     * @param res 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(Boolean res)
    {
        return res ? AjaxResult.success() : AjaxResult.error();
    }
   
}
