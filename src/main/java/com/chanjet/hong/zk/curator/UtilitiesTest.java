/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.zk.curator;

import java.io.IOException;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *    
 * <p>
 * curator utility 测试
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年12月25日 下午2:38:05
 *
 * @version V1.0     
 *     
 */
public class UtilitiesTest {
	static CuratorFramework client;
	@BeforeClass
	public static void beforeClass(){
		String zookeeperConnectionString = "se187:2181,se188:2182,se189:2183/test";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		client.start();
	}
	@AfterClass
	public static void afterClass(){
		client.close();
	}
	
	@Test
	public void testCreatePath() throws Exception {
		String path = "/abc";
		String content = "hahaha";
		client.newNamespaceAwareEnsurePath(path).ensure(client.getZookeeperClient());
		client.setData().forPath(path, content.getBytes());
	}
	@Test
	public void testPath() throws IOException, InterruptedException, KeeperException{
//		ZooKeeper  zk = type name = new type(arguments);
		ZooKeeper  zkClient  = new ZooKeeper("se187:2181/test", 500, null);
		ZKPaths.mkdirs(zkClient, "/hong/abc");
	}
}
