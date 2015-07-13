/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.JavaTest;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 *    
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年11月14日 上午9:30:13
 *
 * @version V1.0     
 *     
 */
@SuppressWarnings("all")
public class TestConvert {

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
	public void test() {
		String aa ="@@bb";
		assertEquals("bb", aa.substring(2));
		
		Map<String, Object> test = Maps.newHashMap();
		test.put("adsf", "sdf");
		test.put("aa", "sss");
		test.put("123aa", "sss");
		test.put("1234aa", "sss");
		Set<String> set = Sets.newHashSet("aa", "lk", "1234aa");
		test.keySet().retainAll(set);
		System.out.println(test);

		String a = (String) test.get("a");
		Boolean b = (Boolean) test.get("b");
		/*
		 * boolean c =b;
		 * System.out.print(c);
		 */
	}
	
	

}
