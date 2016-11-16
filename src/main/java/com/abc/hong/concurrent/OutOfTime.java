/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.abc.hong.concurrent;

import java.util.Timer;
import java.util.TimerTask;
import static java.util.concurrent.TimeUnit.*;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date 2014年12月27日 下午1:46:14
 * 
 * @version V1.0
 * 
 */
public class OutOfTime {
	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		SECONDS.sleep(1);
		timer.schedule(new ThrowTask(), 1);
		SECONDS.sleep(5);
	}

	static class ThrowTask extends TimerTask {
		public void run() {
			throw new RuntimeException();
		}
	}
}
