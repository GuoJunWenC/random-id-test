package org.example.api.requestmerge;

import java.util.concurrent.CompletableFuture;

public class RequestTest {
    //请求条件
    private Long key;
    //异步编程类
    private CompletableFuture<String> future;

    public CompletableFuture<String> getFuture() {
        return future;
    }

    public void setFuture(CompletableFuture<String> future) {
        this.future = future;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
