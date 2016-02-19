package com.chanjet.hong.disruptor;

public class SimpleTracerFactory implements ObjectFactory<CounterTracer, Integer> {

	@Override
	public CounterTracer newInstance(Integer maxCount) {
		return new SimpleTracer(maxCount);
	}


}
