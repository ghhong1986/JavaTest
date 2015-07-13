/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.net;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

/**
 * 
 * <p>
 * 关于网络连接相关测试
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com
 * @date 2014年12月25日 下午8:05:42
 * 
 * @version V1.0
 * 
 */
public class TestNet {
	@Test
	public void testGetIp() throws UnknownHostException {
		// 获取计算机名
		String name = InetAddress.getLocalHost().getHostName();
		// 获取IP地址
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("计算机名：" + name);
		System.out.println("IP地址：" + ip);

	}
	@Test
	public void testGetIP2() throws SocketException {
		Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			System.out.println(netInterface.getName());
			Enumeration<?> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					System.out.println("本机的IP = " + ip.getHostAddress());
				}
			}
		}
	}
	@Test
	public void testProcessId(){
		String name = ManagementFactory.getRuntimeMXBean().getName();  
		System.out.println(name);  
	}

}
