package com.abc.hong.grobo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

class ParalInc {
    public int i=0;    
}


public class ParalIncTest {

    public static class Incrementor extends Thread {
       public void run() {
       }
    }

    @Test
    public void testInc() throws Throwable {
        for (int i = 0; i < 1000; i++) {
            final ParalInc target = new ParalInc();
            
            TestRunnable r = new TestRunnable() {
                public void runTest() throws Throwable {
                synchronized(ParalIncTest.class) {
                    target.i++;
                }
                }
            };
            
            TestRunnable[] tcs = {r, r, r, r, r};
            int threadCount = tcs.length;
            
            MultiThreadedTestRunner mttr = new MultiThreadedTestRunner( tcs );
            mttr.runTestRunnables( 2 * 60 * 1000 );
            
            System.out.println("final value: " + target.i);
            
            if (target.i != threadCount) {
                System.err.println("   Bug - at loop " + i);
            }
            
            assertEquals(threadCount, target.i);
        }
    }
}
