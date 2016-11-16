package com.abc.hong.disruptor;

public class BlockingArrayPublisherFactory implements ObjectFactory<EventPublisher, PublisherCreationArgs>{

	@Override
	public EventPublisher newInstance(PublisherCreationArgs arg) {
		BlockingQueuePublisher publisher = new BlockingQueuePublisher(arg.getMaxEventSize(), arg.getHandler());
		return publisher;
	}

}
