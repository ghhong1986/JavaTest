package test;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CompareTest {
	
	@Test
	public void testCmpList(){
		List<String> list1 = Lists.newArrayList("1234","234");
		List<String> list2 = Lists.newArrayList("1234","234");
		if(list1.equals(list2)){
			System.out.println("equal");
		}else{
			System.out.println("not equal");
		}
		
	}
	
}
