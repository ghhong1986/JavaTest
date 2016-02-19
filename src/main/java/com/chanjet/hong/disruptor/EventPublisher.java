package com.chanjet.hong.disruptor;

/**
 * ���Գ����е�����ߵĳ���
 * 
 * @author haiq
 *
 */
public interface EventPublisher {
	
	public void start();
	
	public void stop();
	
	public void publish(int data) throws Exception;
}
