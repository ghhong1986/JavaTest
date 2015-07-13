/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.concurrent;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com
 * @date 2014年12月27日 下午1:27:30
 * 
 * @version V1.0
 * 
 */
public class TestTask {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSingleThreadWebServer() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (true) {
			Socket connection = socket.accept();
			handleRequest(connection);
		}
	}

	@Test
	public void testThreadPerTaskWebServer() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable() {
				public void run() {
					handleRequest(connection);
				}
			};
			new Thread(task).start();
		}
	}

	private void handleRequest(Socket connection) {
		// TODO Auto-generated method stub

	}

}
