/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.abc.hong.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date 2014年11月29日 下午4:06:00
 * 
 * @version V1.0
 * 
 */
public class TimedCall {
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		long timeout = 1000;// 任务必须在设定时间内完成，否则任务将被强制关闭
		long timeNeed = 2000;// 任务完成需要的时长。
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;// 时间单位
		ExecutorService executor = Executors.newSingleThreadExecutor();// 高级并发API

		Runnable task = new MyThread(timeNeed, timeUnit);

		while (!timedCall(executor, task, timeout, timeUnit))
			;// 在某些场景下，需要不断尝试执行任务，直到能够在限定时间内执行完毕。

	}

	private static boolean timedCall(ExecutorService executor, Runnable c, long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException {
		// FutureTask<?> task = new FutureTask<Object>(c, null);
		// executor.execute(task);
		//
		// task.get(timeout, timeUnit);
		Future<?> future = executor.submit(c);
		try {
			future.get(timeout, timeUnit);
			return true;
		} catch (TimeoutException e) {
			future.cancel(true);// 参数设为true，向执行线程发送中断通知。否则，允许已经启动的线程继续执行直到完成任务。
			System.err.println("任务执行超时,强制退出");
			return false;
		}
	}

	static  class  MyThread implements Runnable {
		long timeLong = 0;// how long thread running will cost
		TimeUnit timeUnit;

		public MyThread() {
		}

		public MyThread(long milli, TimeUnit timeUnit) {
			this.timeLong = milli;
			this.timeUnit = timeUnit;
		}

		@Override
		public void run() {
			System.out.println("---------" + Thread.currentThread().getName() + "开始执行,时长[" + timeLong + "]------");
			try {
				Thread.sleep(timeLong);
			} catch (InterruptedException e) {
				System.err.println("线程中断,退出");
				return;// 必须响应中断，否则无法退出线程。在退出之前你可能还需做一些资源回收等等。
			}
			System.out.println("---------" + Thread.currentThread().getName() + "执行完毕,时长[" + timeLong + "]------");

		}
	}
}
