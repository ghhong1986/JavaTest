/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.zk.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 
 * <p>
 * curator分布式锁
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年11月10日 上午9:20:40
 * 
 * @version V1.0
 * 
 */
public class TestCuratorLock {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(5);

		String zookeeperConnectionString = "se187:2181,se188:2182,se189:2183/distlock";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		client.start();
		System.out.println("客户端启动。。。。");
		ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++) {
			exec.submit(new MyLock("client" + i, client, latch));
		}

		exec.shutdown();
		latch.await();
		System.out.println("所有任务执行完毕");

		client.close();

		System.out.println("客户端关闭。。。。");
	}

	static class MyLock implements Runnable {

		private String name;

		private CuratorFramework client;

		private CountDownLatch latch;

		public MyLock(String name, CuratorFramework client, CountDownLatch latch) {
			this.name = name;
			this.client = client;
			this.latch = latch;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			InterProcessMutex lock = new InterProcessMutex(client, "/javatest/mutex");
			try {
				while (lock.acquire(120, TimeUnit.SECONDS)) {

					try {
						// do some work inside of the critical section here
						System.out.println("----------" + this.name + "获得资源----------");
						System.out.println("----------" + this.name + "正在处理资源----------");
						Thread.sleep(10 * 1000);
						System.out.println("----------" + this.name + "资源使用完毕----------");
						latch.countDown();
					} finally {
						lock.release();
						System.out.println("----------" + this.name + "释放----------");
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
