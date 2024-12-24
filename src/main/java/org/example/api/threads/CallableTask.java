package org.example.api.threads;

import java.util.concurrent.*;
import java.util.stream.Stream;

public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static void main(String[] args) {    // 仅简单举例，在生产代码中可别这么写！
        // 统计耗时的函数
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> result = Stream.of(1, 2)
                // 创建异步任务
                .map(x -> CompletableFuture.supplyAsync(() -> getCount(x), executor))
                // 聚合
                .reduce(CompletableFuture.completedFuture(1), (x, y) -> x.thenCombineAsync(y, Integer::sum, executor));
        // 等待结果
        try {
            System.err.println("结果：" + result.get());
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("任务执行异常");
        }
    }

    public static Integer getCount(Integer x) {
        return x;
    }
}
