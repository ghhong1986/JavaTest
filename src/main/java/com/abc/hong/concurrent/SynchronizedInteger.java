/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.abc.hong.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date Dec 24, 2014 7:19:39 PM
 * 
 * @version V1.0
 * 
 */
@ThreadSafe
public class SynchronizedInteger {
	@GuardedBy("this")
	private int value;

	public synchronized int get() {
		return value;
	}

	public synchronized void set(int value) {
		this.value = value;
	}
}
