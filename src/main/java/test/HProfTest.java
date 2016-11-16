package test;

/**
 * PROJECT_NAME: test
 * DATE:         16/7/22
 * CREATE BY:    chao.cheng
 * 
 * -agentlib:hprof=cpu=times,interval=10
 **/
public class HProfTest {
    public void slowMethod() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void slowerMethod() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HProfTest test = new HProfTest();
        test.slowerMethod();
        test.slowMethod();
    }
}
