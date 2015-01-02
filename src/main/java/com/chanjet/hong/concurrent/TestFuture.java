/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;

/**
 * 
 * <p>
http://blog.csdn.net/minword/article/details/20556901
http://blog.csdn.net/minword/article/details/20565867

   Executor框架是指java5中引入的一系列并发库中与executor相关的功能类，
   包括Executor、Executors、ExecutorService、CompletionService、Future、Callable等
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年11月29日 下午2:25:26
 * 
 * @version V1.0
 * 
 */
@SuppressWarnings("all")
public class TestFuture {
	public String downloadContents(URL url) throws IOException {
		try (InputStream input = url.openStream()) {
			return IOUtils.toString(input, StandardCharsets.UTF_8);
		}
	}

	private final ExecutorService pool = Executors.newFixedThreadPool(10);
/*
 * 虽然有大量的繁琐的语法问题，但是基本思想是简单的： 把需要长时间运行的计算包装到可调用的<String>，
 * 并submit()到线程池,这个线程池包含10个线程。 提交后返回Future<String>的实现，就像以某种方式链接到
 * 你的任务和线程池。明显的你的任务不会被立即执行，相反它被放到一个队列中，稍后会被线程拉出来， 现
 * 在需要搞清楚cancel()的两个特别的意义是什么——你可以取消在队列中停留的任务，也可以取消早已运行的
 * 任务，但这是一件比较复杂的事情。
 */
	public Future<String> startDownloading(final URL url) {
		return pool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				try (InputStream input = url.openStream()) {
					return IOUtils.toString(input, StandardCharsets.UTF_8);
				}
			}
		});
	}

	public void testDownload() throws MalformedURLException, InterruptedException, ExecutionException {
		final Future<String> contentsFuture = startDownloading(new URL("http://www.example.com"));
		// other computation
		while (!contentsFuture.isDone()) {
			askUserToWait();
			doSomeComputationInTheMeantime();
		}
		final String contents = contentsFuture.get();

		// 中间可以调用取消的接口
		contentsFuture.cancel(true); // meh...
	}

	private void doSomeComputationInTheMeantime() {
		// TODO Auto-generated method stub

	}

	private void askUserToWait() {
		// TODO Auto-generated method stub

	}
}
