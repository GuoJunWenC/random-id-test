package org.example.api.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DisruptorManager<T> {
    private static final Integer DEFAULT_CONSUMER_SIZE = 4;

    public static final Integer DEFAULT_SIZE = 4096 << 1 << 1;

    private DataEventListener<T> dataEventListener;

    private DisruptorProducer<T> producer;

    private int ringBufferSize;

    private int consumerSize;

    public DisruptorManager(DataEventListener<T> dataEventListener) {
        this(dataEventListener, DEFAULT_CONSUMER_SIZE, DEFAULT_SIZE);
    }

    public DisruptorManager(DataEventListener<T> dataEventListener, final int consumerSize, final int ringBufferSize) {
        this.dataEventListener = dataEventListener;
        this.ringBufferSize = ringBufferSize;
        this.consumerSize = consumerSize;
    }

    public void start() {
        EventFactory<DataEvent<T>> eventFactory = new DisruptorEventFactory();
        Disruptor<DataEvent<T>> disruptor = new Disruptor<>(
                eventFactory,
                ringBufferSize,
                new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy()),
                ProducerType.MULTI,
                new BlockingWaitStrategy()
        );
        DisruptorConsumer<T>[] consumers = new DisruptorConsumer[consumerSize];
        for (int i = 0; i < consumerSize; i++) {
            consumers[i] = new DisruptorConsumer<>(dataEventListener);
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        disruptor.start();
        RingBuffer<DataEvent<T>> ringBuffer = disruptor.getRingBuffer();
        this.producer = new DisruptorProducer<>(ringBuffer, disruptor);
    }

    public DisruptorProducer getProducer() {
        return this.producer;
    }
}
