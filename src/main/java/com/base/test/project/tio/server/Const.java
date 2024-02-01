/**
 * 
 */
package com.base.test.project.tio.server;

import java.util.Random;

/**
 * @author tanyaowu
 *
 */
public class Const {
	/**
	 * 用于群聊的group id
	 */
	public static final String GROUP_ID_PREFIX= "ws-";

	public static final String ONETONE = "1V1";

	public static final Integer DELSTATUS_1= 1;
	public static final Integer DELSTATUS_2= 2;

	public static final int TIMES_MINUTE_5 = 5 * 60 * 1000 + getSmallRandomInt()*1000;

	private static int getSmallRandomInt() {
		Random random = new Random();
		return random.nextInt(60);
	}
}
