/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.abc.hong.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email ghhong1986
 * @date 2014年11月28日 下午3:39:51
 * 
 * @version V1.0
 * 
 */
public class ThreadTest {
	public List<Commit> getCommits(String objectId, String path, int offset, int maxCount) throws InterruptedException, ExecutionException {
		List<String> shas = Lists.newArrayList(); // getCommitsSha(this,
		                                          // objectId, path, offset,
		                                          // maxCount);
		List<Commit> commits = new ArrayList<>();

		if (shas != null) {
			List<GetCommit> getCommits = new ArrayList<>();
			for (String sha : shas) {
				getCommits.add(new GetCommit(null, sha));
			}

			// 声明一个自适应的线程池
			ExecutorService executor = Executors.newFixedThreadPool(8);

			List<Future<Commit>> futureList = null;

			// 并发的调用getCommit
			futureList = executor.invokeAll(getCommits);
			executor.shutdown();

			for (Future<Commit> future : futureList) {
				Commit commit = future.get();
				commits.add(commit);
			}
		}
		return commits;
	}
}
