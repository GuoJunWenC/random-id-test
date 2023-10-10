package org.example.api.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisruptorProducer<T> {
    private final Logger logger = LoggerFactory.getLogger(DisruptorProducer.class);

    private final RingBuffer<DataEvent<T>> ringBuffer;

    private final Disruptor<DataEvent<T>> disruptor;
    private final EventTranslatorOneArg<DataEvent<T>, T> translatorOneArg = (tDataEvent, l, t) -> tDataEvent.setData(t);

    public DisruptorProducer(final RingBuffer<DataEvent<T>> ringBuffer, final Disruptor<DataEvent<T>> disruptor) {
        this.ringBuffer = ringBuffer;
        this.disruptor = disruptor;
    }

    /**
     * Send a data.
     *
     * @param data the data
     */
    public void onData(final T data) {
        try {
            ringBuffer.publishEvent(translatorOneArg, data);
        } catch (Exception ex) {
            logger.error("publish event error:", ex);
        }
    }

    public void shutdown() {
        if (null != disruptor) {
            disruptor.shutdown();
        }
    }
}
