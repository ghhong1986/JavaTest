package test;

/**
 * PROJECT_NAME: test
 * DATE:         16/7/22
 * CREATE BY:    chao.cheng
 **/
/* 
 * pidstat -p 843 1 3 -u -t
-u：代表对cpu使用率的监控
参数1 3：表示每秒采样一次，一共三次
-t：将监控级别细化到线程 
*/
public class PidstatTest {
    public static class PidstatTask implements  Runnable {
        public void run() {
            while(true) {
                double value = Math.random() * Math.random();
            }
        }
    }

    public static class LazyTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new PidstatTask()).start();
        new Thread(new LazyTask()).start();
        new Thread(new LazyTask()).start();
    }
}