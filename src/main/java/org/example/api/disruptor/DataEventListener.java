package org.example.api.disruptor;

public interface DataEventListener<T> {
    void processDataEvent(DataEvent<T> dataEvent);
}
