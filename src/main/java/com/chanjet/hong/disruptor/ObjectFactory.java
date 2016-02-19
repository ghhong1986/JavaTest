package com.chanjet.hong.disruptor;

public interface ObjectFactory<T, TA> {

	T newInstance(TA arg);
}
