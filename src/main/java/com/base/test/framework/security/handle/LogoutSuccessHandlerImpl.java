package com.base.test.framework.security.handle;

import cn.hutool.core.lang.Validator;
import cn.hutool.http.HttpStatus;
import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.utils.JsonUtils;
import com.base.test.common.utils.ServletUtils;
import com.base.test.framework.web.service.AsyncService;
import com.base.test.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author baseWork
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AsyncService asyncService;

	/**
	 * 退出处理
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException, ServletException {
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (Validator.isNotNull(loginUser)) {
			String userName = loginUser.getUsername();
			// 删除用户缓存记录
			tokenService.delLoginUser(loginUser.getToken());
			// 记录用户退出日志
			asyncService.recordLogininfor(userName, Constants.LOGOUT, "退出成功", request);
		}
		ServletUtils.renderString(response, JsonUtils.toJsonString(AjaxResult.error(HttpStatus.HTTP_OK, "退出成功")));
	}

}
