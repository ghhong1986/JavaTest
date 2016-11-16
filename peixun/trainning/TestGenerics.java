/* 
 *  智能平台
 *
 * 版本信息：2016年2月23日    
 *
 * Copyright 畅捷通信息技术股份有限公司  @ 2016 版权所有    
 *    
 */
package trainning;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * TODO 
 *</p>
 * @author 洪光华 </br>
 * @Email ghhong1986 
 * @date 2016年2月23日 下午8:43:15
 */
public class TestGenerics {

    /** 
     * <p>
     * TODO 
     * </p>
     * @throws java.lang.Exception
     *
     * @author : 洪光华
     * @date : 2016年2月23日
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    /** 
     * <p>
     * TODO 
     * </p>
     * @throws java.lang.Exception
     *
     * @author : 洪光华
     * @date : 2016年2月23日
     */
    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() {
        List<?>[] lsa = new List<?>[10];
        Object o = lsa;
        Object[] oa = (Object[]) o;
        List<Integer> li = new ArrayList<Integer>();
        li.add(new Integer(3));
        // Correct.
        oa[1] = li;
        // Run time error, but cast is explicit.
        Integer s = (Integer) lsa[1].get(0);
        

            
    }

}
