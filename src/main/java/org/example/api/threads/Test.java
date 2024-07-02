package org.example.api.threads;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
   /* public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableTask mc = new CallableTask();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }*/
   public static void main(String[] args) {
       ThreadPoolExecutor executorOne = new ThreadPoolExecutor(5, 5, 1,
               TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(20), new CustomizableThreadFactory("Tianluo-Thread-pool"));
       executorOne.execute(() -> {
           System.out.println("关注公众号：捡田螺的小男孩");
       });

       //关闭线程池
       executorOne.shutdown();
   }
}
