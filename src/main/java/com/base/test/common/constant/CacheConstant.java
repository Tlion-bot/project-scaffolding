package com.base.test.common.constant;

import java.time.Duration;

public interface CacheConstant {

	String LOCAL_LOGIN_USER_KEY = "login_key:";
	
	String LOCAL_LOGIN_TOKEN_KEY = "login_user_key:";
	
	String CAPTCHA_CODE_KEY = "captcha_codes:";
	
	int EXPIRE_TIME = 24*60*60;//秒
	
	int MILLIS_SECOND = 1000;
	
	int REFRESH_TIME = 1200*1000;//毫秒  20分钟
	
	int CODE_EXPIRE_TIME = 300;//验证码失效时间
	
	String PHONE_CODE_KEY = "phone_code:";

	Duration PHONE_CODE_KEY_EXPIRATION = Duration.ofMinutes(5);

	String PHONE_CODE_CHECK_TIMES_PREFIX = "phone_code_check_times_prefix:";

	int PHONE_CODE_TOTAL_CHECK_TIMES = 10;

	String SUNVEGA_TOKEN = "sunvega_token";

	String LOCK_GAI = "lock_gai_";

	Duration TRY_LOCK_TIME = Duration.ofSeconds(10L);
}
