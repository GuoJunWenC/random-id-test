package org.example.api.disruptor;

import com.lmax.disruptor.WorkHandler;

public class DisruptorConsumer<T> implements WorkHandler<DataEvent<T>> {
    private DataEventListener<T> dataEventListener;

    public DisruptorConsumer(DataEventListener dataEventListener) {
        this.dataEventListener = dataEventListener;
    }

    @Override
    public void onEvent(DataEvent<T> dataEvent) {
        if (dataEvent != null) {
            dataEventListener.processDataEvent(dataEvent);
        }
    }
}
