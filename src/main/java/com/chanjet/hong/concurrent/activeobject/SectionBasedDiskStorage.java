/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent.activeobject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年11月29日 上午11:13:52
 * 
 * @version V1.0
 * 
 */
@SuppressWarnings("all")
public class SectionBasedDiskStorage {
	private Deque sectionNames = new LinkedList();
	/*
	 * Key->value: 存储子目录名->子目录下缓存文件计数器
	 */
	private Map sectionFileCountMap = new HashMap();
	private int maxFilesPerSection = 2000;
	private int maxSectionCount = 100;
	private String storageBaseDir = System.getProperty("user.dir") + "/vpn";

	private final Object sectionLock = new Object();

	public String[] apply4Filename(MMSDeliverRequest request) {
		String sectionName;
		int iFileCount;
		boolean need2RemoveSection = false;
		String[] fileName = new String[2];
		synchronized (sectionLock) {
			// 获取当前的存储子目录名
			sectionName = this.getSectionName();
			AtomicInteger fileCount;
			fileCount = (AtomicInteger) sectionFileCountMap.get(sectionName);
			iFileCount = fileCount.get();
			// 当前存储子目录已满
			if (iFileCount >= maxFilesPerSection) {
				if (sectionNames.size() >= maxSectionCount) {
					need2RemoveSection = true;
				}
				// 创建新的存储子目录
				sectionName = this.makeNewSectionDir();
				fileCount = (AtomicInteger) sectionFileCountMap.get(sectionName);

			}
			iFileCount = fileCount.addAndGet(1);

		}

		fileName[0] = storageBaseDir + "/" + sectionName + "/" + new DecimalFormat("0000").format(iFileCount) + "-" + request.getTimeStamp().getTime() / 1000 + "-" + request.getExpiry() + ".rq";
		fileName[1] = sectionName;

		if (need2RemoveSection) {
			// 删除最老的存储子目录
			String oldestSectionName = (String) sectionNames.removeFirst();
			this.removeSection(oldestSectionName);
		}

		return fileName;
	}

	public void decrementSectionFileCount(String sectionName) {
		AtomicInteger fileCount = (AtomicInteger) sectionFileCountMap.get(sectionName);
		if (null != fileCount) {
			fileCount.decrementAndGet();
		}
	}

	private boolean removeSection(String sectionName) {
		boolean result = true;
		File dir = new File(storageBaseDir + "/" + sectionName);
		for (File file : dir.listFiles()) {
			result = result && file.delete();
		}
		result = result && dir.delete();
		return result;
	}

	private String getSectionName() {
		String sectionName;

		if (sectionNames.isEmpty()) {
			sectionName = this.makeNewSectionDir();

		} else {
			sectionName = (String) sectionNames.getLast();
		}

		return sectionName;
	}

	private String makeNewSectionDir() {
		String sectionName;
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		sectionName = sdf.format(new Date());
		File dir = new File(storageBaseDir + "/" + sectionName);
		if (dir.mkdir()) {
			sectionNames.addLast(sectionName);
			sectionFileCountMap.put(sectionName, new AtomicInteger(0));
		} else {
			throw new RuntimeException("Cannot create section dir " + sectionName);
		}

		return sectionName;
	}
}
