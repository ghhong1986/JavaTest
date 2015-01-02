/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.zk.curator;

/**
 * 
 * <p>
 * Recipes， ZooKeeper的系列recipe实现, 基于 Curator Framework.
 * Framework， 封装了大量ZooKeeper常用API操作，降低了使用难度,
 * 基于Zookeeper增加了一些新特性，对ZooKeeper链接的管理，对链接丢失自动重新链接。
 * Utilities，一些ZooKeeper操作的工具类包括ZK的集群测试工具路径生成等非常有用，在Curator-Client包下org.apache.
 * curator.utils。
 * Client，ZooKeeper的客户端API封装，替代官方 ZooKeeper class，解决了一些繁琐低级的处理，提供一些工具类。
 * Errors，异常处理, 连接异常等
 * Extensions，对curator-recipes的扩展实现，拆分为
 * curator-:stuck_out_tongue_closed_eyes:iscovery和
 * curator-:stuck_out_tongue_closed_eyes:iscovery-server提供基于RESTful的Recipes
 * WEB服务
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年12月25日 下午2:29:14
 * 
 * @version V1.0
 * 
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

public class LeaderLatchExample {
	private static final int CLIENT_QTY = 10;
	private static final String PATH = "/examples/leader";

	public static void main(String[] args) throws Exception {

		List<CuratorFramework> clients = Lists.newArrayList();
		List<LeaderLatch> examples = Lists.newArrayList();
		TestingServer server = new TestingServer();
		try {
			for (int i = 0; i < CLIENT_QTY; ++i) {
				CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
				clients.add(client);
				LeaderLatch example = new LeaderLatch(client, PATH, "Client #" + i);
				examples.add(example);
				client.start();
				example.start();
			}

			Thread.sleep(20000);

			LeaderLatch currentLeader = null;
			for (int i = 0; i < CLIENT_QTY; ++i) {
				LeaderLatch example = examples.get(i);
				if (example.hasLeadership())
					currentLeader = example;
			}
			System.out.println("current leader is " + currentLeader.getId());
			System.out.println("release the leader " + currentLeader.getId());
			currentLeader.close();
			examples.get(0).await(2, TimeUnit.SECONDS);
			System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
			System.out.println("the new leader is " + examples.get(0).getLeader().getId());

			System.out.println("Press enter/return to quit\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Shutting down...");
			for (LeaderLatch exampleClient : examples) {
				CloseableUtils.closeQuietly(exampleClient);
			}
			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}
			CloseableUtils.closeQuietly(server);
		}
	}
}