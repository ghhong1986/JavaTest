/*
 * Copyright 畅捷通股份有限公司 @ 2014 版权所有
 */
package com.chanjet.hong.concurrent;

/**
 * 
 * <p>
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。<br>
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为<br>
 * 该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。CyclicBarrier 支持一个可选的 Runnable
 * 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。
 * 若在继续所有参与线程之前更新共享状态，此屏障操作 很有用
 * 
 * //设置parties、count及barrierCommand属性。
 * CyclicBarrier(int):
 * 
 * //当await的数量到达了设定的数量后，首先执行该Runnable对象。
 * CyclicBarrier(int,Runnable):
 * 
 * //通知barrier已完成线程
 * await():
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年12月27日 上午11:46:35
 * 
 * @version V1.0
 * 
 */
public class TestBarrier {

}
