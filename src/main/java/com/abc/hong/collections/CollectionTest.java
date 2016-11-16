/* 
 * Copyright 畅捷通股份有限公司  @ 2015 版权所有    
 *    
 */
package com.abc.hong.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *    
 * <p>
 *  集合操作的测试用例
 *</p>
 * @author 洪光华 <br/>
 * @Email ghhong1986 
 * @date 2015年2月14日 下午2:37:15
 *
 * @version V1.0     
 *     
 */
public class CollectionTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	//@Test(expected=ConcurrentModificationException.class )
	@Test
	public void testCollectionRemoveException() {
		expectedException.expect(ConcurrentModificationException.class);
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		for (String s : list) {
			if (s.equals("B")) {
				list.remove(s);
			}
		}
	}

	@Test
	public void testListRemove() {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			String str = iter.next();
			if (str.equals("B")) {
				iter.remove();
			}
		}
		
		//another solution
		List<String> list2 = new CopyOnWriteArrayList<String>(list);
		for (String s : list2) {
			if (s.equals("B")) {
				list2.remove(s);
			}
		}

		//Set LinkList   remove while   iterating
		
		Set<String> set = new HashSet<String>();
		set.add("A");
		set.add("B");
		for (String s : set) {
			if (s.equals("B")) {
				set.remove(s);
			}
		}
		
		LinkedList<String> llist = new LinkedList<String>();
		llist.add("A");
		llist.add("B");
		for (String s : llist) {
			if (s.equals("B")) {
				llist.remove(s);
			}
		}
		
		
	}

}
