/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.zk.curator;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com
 * @date 2014年12月25日 下午3:06:02
 * 
 * @version V1.0
 * 
 */
import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author qindongliang
 *         curator操作zookeeper的
 *         基本例子
 * **/
public class CuratorTools {

	static CuratorFramework zkclient = null;
	static String nameSpace = "test";  // namespace不能有反斜杠  ，但是其它到路径必须要有,否则就会出现异常
	static {

		String zkhost = "se187:2181";// zk的host
		RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);// 重试机制
		Builder builder = CuratorFrameworkFactory.builder().connectString(zkhost).connectionTimeoutMs(5000).sessionTimeoutMs(5000).retryPolicy(rp);
		builder.namespace(nameSpace);  
		CuratorFramework zclient = builder.build();
		zkclient = zclient;
		zkclient.start();// 放在这前面执行
	}

	public static void main(String[] args) throws Exception {
		CuratorTools ct = new CuratorTools();
		ct.getListChildren("/");
		ct.upload("/jianli/desktop", "/home/hong/examples.desktop");
		// ct.createrOrUpdate("/tt/cc334/zzz","c");
		// ct.delete("/qinb/bb");
		// ct.checkExist("/zk");
		//ct.read("/jianli/123.txt");
		zkclient.close();

	}
	
	
	public void assurePath(String path) throws Exception {
		zkclient.newNamespaceAwareEnsurePath(path).ensure(zkclient.getZookeeperClient());
	}
	

	/**
	 * 创建或更新一个节点
	 * 
	 * @param path
	 *                路径
	 * @param content
	 *                内容
	 * **/
	public void createrOrUpdate(String path, String content) throws Exception {

		zkclient.newNamespaceAwareEnsurePath(path).ensure(zkclient.getZookeeperClient());
		zkclient.setData().forPath(path, content.getBytes());
		System.out.println("添加成功！！！");

	}

	/**
	 * 删除zk节点
	 * 
	 * @param path
	 *                删除节点的路径
	 * 
	 * **/
	public void delete(String path) throws Exception {
		zkclient.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
		System.out.println("删除成功!");
	}

	/**
	 * 判断路径是否存在
	 * 
	 * @param path
	 * **/
	public void checkExist(String path) throws Exception {

		if (zkclient.checkExists().forPath(path) == null) {
			System.out.println("路径不存在!");
		} else {
			System.out.println("路径已经存在!");
		}

	}

	/**
	 * 读取的路径
	 * 
	 * @param path
	 * **/
	public void read(String path)throws Exception{
		String data=new String(zkclient.getData().forPath(path),"gbk");
		System.out.println("读取的数据:" +data);
	}

	/**
	 * @param path
	 *                路径
	 *                获取某个节点下的所有子文件
	 * */
	public void getListChildren(String path) throws Exception {

		List<String> paths = zkclient.getChildren().forPath(path);
		for (String p : paths) {
			System.out.println(p);
		}

	}

	/**
	 * @param zkPath
	 *                zk上的路径
	 * @param localpath
	 *                本地上的文件路径
	 * 
	 * **/
	public void upload(String zkPath, String localpath) throws Exception {

		createrOrUpdate(zkPath, "");// 创建路径
		byte[] bs = FileUtils.readFileToByteArray(new File(localpath));
		zkclient.setData().forPath(zkPath, bs);
		System.out.println("上传文件成功！");

	}

}