/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.abc.hong.zk.curator;

/**
 * 
 * <p>
 * 栅栏，参考 http://ifeve.com/zookeeper-barrier/
 * 分布式Barrier是这样一个类： 它会阻塞所有节点上的等待进程，知道某一个被满足， 然后所有的节点继续进行。
 * 
 * 双栅栏允许客户端在计算的开始和结束时同步。当足够的进程加入到双栅栏时，进程开始计算， 当计算完成时，
 * 离开栅栏。 双栅栏类是DistributedDoubleBarrier。 
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date 2014年12月25日 下午2:10:07
 * 
 * @version V1.0
 * 
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

public class DistributedBarrierExample {
	private static final int QTY = 5;
	private static final String PATH = "/examples/barrier";

	public static void main(String[] args) throws Exception {
		try (TestingServer server = new TestingServer()) {
			CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
			client.start();

			ExecutorService service = Executors.newFixedThreadPool(QTY);
			DistributedBarrier controlBarrier = new DistributedBarrier(client, PATH);
			controlBarrier.setBarrier();

			for (int i = 0; i < QTY; ++i) {
				final DistributedBarrier barrier = new DistributedBarrier(client, PATH);
				final int index = i;
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {

						Thread.sleep((long) (3 * Math.random()));
						System.out.println("Client #" + index + " waits on Barrier");
						barrier.waitOnBarrier();
						System.out.println("Client #" + index + " begins");
						return null;
					}
				};
				service.submit(task);
			}

			Thread.sleep(10000);
			System.out.println("all Barrier instances should wait the condition");

			controlBarrier.removeBarrier();

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);

		}

	}

}
