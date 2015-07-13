/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.JavaTest;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;

/**
 *    
 * <p>
 * 集合类相关代码测试
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年12月27日 上午11:14:38
 *
 * @version V1.0     
 *     
 */
public class TestCollections {

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
		Vector<Integer> vect = new Vector<>();
		vect.add(1);
		vect.add(3);
		vect.add(9);
		vect.add(5);
		Iterator<Integer> iter =vect.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
			iter.remove();
                }
		System.out.println("again");
		 iter =vect.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
			iter.remove();
		}

 	}
	
	public void testList(){
		CopyOnWriteArrayList  llst = new CopyOnWriteArrayList<Integer>();
		ConcurrentMap<Integer, String> map = Maps.newConcurrentMap();
		map.putIfAbsent(89, "");
	}

}
