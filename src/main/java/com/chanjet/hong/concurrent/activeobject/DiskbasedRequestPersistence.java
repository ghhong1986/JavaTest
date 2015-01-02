/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent.activeobject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年11月29日 上午11:09:14
 * 
 * @version V1.0
 * 
 */
public class DiskbasedRequestPersistence implements RequestPersistence {

	// 负责缓存文件的存储管理
	private final SectionBasedDiskStorage storage = new SectionBasedDiskStorage();
	private final Logger logger = Logger.getLogger(DiskbasedRequestPersistence.class);

	@Override
	public void store(MMSDeliverRequest request) {
		// 申请缓存文件的文件名
		String[] fileNameParts = storage.apply4Filename(request);
		File file = new File(fileNameParts[0]);
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
			try {
				objOut.writeObject(request);
			} finally {
				objOut.close();
			}
		} catch (FileNotFoundException e) {
			storage.decrementSectionFileCount(fileNameParts[1]);
			logger.error("Failed to store request", e);
		} catch (IOException e) {
			storage.decrementSectionFileCount(fileNameParts[1]);
			logger.error("Failed to store request", e);
		}

	}

}
