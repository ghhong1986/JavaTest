package com.chanjet.hong.disruptor;

public class DirectingPublisher implements EventPublisher {
	
	private TestHandler handler;
	
	private TestEvent event = new TestEvent();
	
	public DirectingPublisher(TestHandler handler) {
		this.handler = handler;
	}

	@Override
	public void publish(int data) throws Exception {
		event.setValue(data);
		handler.process(event);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
