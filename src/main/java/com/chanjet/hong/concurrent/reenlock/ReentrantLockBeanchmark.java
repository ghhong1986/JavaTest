/*
 * Copyright 畅捷通股份有限公司 @ 2015 版权所有
 */
package com.chanjet.hong.concurrent.reenlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 </br>
 */
public class ReentrantLockBeanchmark implements Counter {

	private volatile long count = 0;

	private Lock lock;

	public ReentrantLockBeanchmark() {
		// 使用非公平锁，true就是公平锁
		lock = new ReentrantLock(false);
	}

	public long getValue() {
		return count;
	}

	public void increment() {
		lock.lock();
		try {
			count++;
		} finally {
			lock.unlock();
		}
	}

}
