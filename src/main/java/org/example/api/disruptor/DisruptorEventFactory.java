package org.example.api.disruptor;

import com.lmax.disruptor.EventFactory;

public class DisruptorEventFactory<T> implements EventFactory<DataEvent<T>> {
    @Override
    public DataEvent<T> newInstance() {
        return new DataEvent<>();
    }
}
