package com.base.test.feignclient;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.constant.ApiConstants;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.domain.req.PageUser;
import feign.Headers;
import feign.RequestLine;

/**
 * @author nnc
 * @date 2023/11/14 16:13
 */
public interface UserFeignClient {
    // @RequestLine(ApiConstants.PAGELIST_POST + "?handle={handle}&cache={cache}")
    // CommonResp<IdentityQueryResp> query(@Param("handle") String handle, @Param("cache") Long cache);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine(ApiConstants.PAGELIST_POST )
    AjaxResult<Page<User>> pageList(PageUser user);

}