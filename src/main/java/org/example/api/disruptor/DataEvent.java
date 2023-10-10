package org.example.api.disruptor;

import java.io.Serializable;

public class DataEvent<T> implements Serializable {
    private static final long serialVersionUID = -5357550106485583011L;
    private T data;

    public DataEvent(T data) {
        this.data = data;
    }

    public DataEvent() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
