/* 
 * Copyright 畅捷通股份有限公司  @ 2015 版权所有    
 *    
 */
package com.abc.hong.lock.zk3;

import java.util.Random;

/**
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 */
public class RandomUtils {
	private static Random  random  = new Random(System.currentTimeMillis());
	public static int nextInt(int i) {
		return random.nextInt(i);
        }
	

}
