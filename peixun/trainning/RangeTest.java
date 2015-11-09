package trainning;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RangeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Range rangeleft = new Range(123,true,1000,false);
		assertTrue(rangeleft.isInRange(123));
		assertFalse(rangeleft.isInRange(1000));
		assertTrue(rangeleft.isInRange(125));
		
		Range rangeright= new Range(123,false,1000,true);
		assertFalse(rangeright.isInRange(123));
		assertTrue(rangeright.isInRange(1000));
		assertTrue(rangeright.isInRange(125));
		
		Range rangeBB= new Range(123,true,1000,true);
		assertTrue(rangeBB.isInRange(123));
		assertTrue(rangeBB.isInRange(1000));
		assertTrue(rangeBB.isInRange(125));
		
		
		Range rangeKK= new Range(123,false,1000,false);
		assertFalse(rangeKK.isInRange(123));
		assertFalse(rangeKK.isInRange(1000));
		assertTrue(rangeKK.isInRange(125));
		
		//Given ... when ... then 
		
	}
	
	@Test(expected=Exception.class)
	public void NumberInMiddleOfRangeContianed(){
		int min = 123;
		int max = 1000;
		Range rangeleft = new Range(min,true,max,false);
		assertTrue(rangeleft.isInRange(min+2));
		assertTrue(rangeleft.isInRange(max-2));
		
	}
	
	
	
	@Test
	public void NumberOnBounderOfRangeContained(){
		int min = 123;
		int max = 1000;
		Range rangeleft = new Range(min,true,max,false);
		assertTrue(rangeleft.isInRange(min));
		assertFalse(rangeleft.isInRange(max));
		
		//
		Range rangeright = new Range(min,false,max,true);
		assertFalse(rangeright.isInRange(min));
		assertTrue(rangeright.isInRange(max));
		
		
	}
	
	

}
