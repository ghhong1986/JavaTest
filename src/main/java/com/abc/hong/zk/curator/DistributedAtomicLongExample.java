/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.abc.hong.zk.curator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * 再看一个Long类型的计数器。 除了计数的范围比SharedCount大了之外， 它首先尝试使用乐观锁的方式设置计数器，
 * 如果不成功(比如期间计数器已经被其它client更新了)， 它使用InterProcessMutex方式来更新计数值。
 * 还记得InterProcessMutex是什么吗
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date 2014年12月25日 下午2:05:13
 * 
 * @version V1.0
 * 
 */
public class DistributedAtomicLongExample {
	private static final int QTY = 5;
	private static final String PATH = "/examples/counter";

	public static void main(String[] args) throws IOException, Exception {
		try (TestingServer server = new TestingServer()) {
			CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
			client.start();

			List<DistributedAtomicLong> examples = Lists.newArrayList();
			ExecutorService service = Executors.newFixedThreadPool(QTY);
			for (int i = 0; i < QTY; ++i) {
				final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

				examples.add(count);
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						try {
							// Thread.sleep(rand.nextInt(1000));
							AtomicValue<Long> value = count.increment();
							// AtomicValue<Long>
							// value =
							// count.decrement();
							// AtomicValue<Long>
							// value =
							// count.add((long)rand.nextInt(20));
							System.out.println("succeed: " + value.succeeded());
							if (value.succeeded())
								System.out.println("Increment: from " + value.preValue() + " to " + value.postValue());
						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}
				};
				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);
		}

	}

}
