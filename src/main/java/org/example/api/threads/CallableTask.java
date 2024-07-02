package org.example.api.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() {
        return ThreadLocalRandom.current().nextInt();
    }
}
