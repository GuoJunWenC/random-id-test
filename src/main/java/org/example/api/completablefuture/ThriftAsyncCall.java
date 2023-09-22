package org.example.api.completablefuture;
@FunctionalInterface
public interface ThriftAsyncCall {
    void invoke() throws Exception ;
}
