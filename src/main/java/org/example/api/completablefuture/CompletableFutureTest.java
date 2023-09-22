package org.example.api.completablefuture;

import lombok.SneakyThrows;
import org.elasticsearch.search.aggregations.metrics.ParsedSingleValueNumericMetricsAggregation;

import java.util.concurrent.*;

public class CompletableFutureTest {


    /* @SneakyThrows
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(1);
                return "ok";
            }

        });
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }*/
  /*  @SneakyThrows
   public static void main(String[] args) {
       // 创建一个Callable对象，用于计算平方数
       Callable<Integer> callable = new Callable<Integer>() {
           @Override
           public Integer call() throws Exception {
               int num = 10;
               int result = 0;
               for (int i = 1; i <= num; i++) {
                   result += i * i;
               }
               return result;
           }
       };

       // 在线程池中执行FutureTask对象，并获取Future对象
       ExecutorService executor = Executors.newFixedThreadPool(1);
       Future<Integer> future = executor.submit(callable);

       // 在另一个线程中执行其他任务
       Thread.sleep(2000);

       // 获取Future对象的结果，如果计算还未完成，会阻塞等待
       int result = future.get();
       System.out.println("计算结果为：" + result);

       // 关闭线程池
       executor.shutdown();
   }*/

    /* @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "何时能财富自由,这班一天也不想上了");
        while (!future.isDone()) {
            //任务有没有完成，没有就继续循环判断
        }
        System.out.println(future.get());
        executorService.shutdown();
    }*/
 /*   @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try{
                System.out.println(Thread.currentThread().getName() + "子线程开始干活");
                //子线程睡 5 秒
                Thread.sleep(5000);
                //在子线程中完成主线程
                System.out.println(future.completeExceptionally(new RuntimeException("结束一场")));  ;
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "A").start();
        //主线程调用 get 方法阻塞
        System.out.println("主线程调用 get 方法获取结果为: " + future.get());
        System.out.println("主线程完成,阻塞结束!!!!!!");
    }*/
  /*  @SneakyThrows
   public static void main(String[] args) {
       //可以自定义线程池
       ExecutorService executor = Executors.newCachedThreadPool();
       //runAsync的使用
       CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> System.out.println("run,润到东欧"), executor);
       //supplyAsync的使用
       CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
           System.out.print("supply,魂牵梦萦");
           return "看星星"; }, executor);
       //runAsync的future没有返回值，输出null
       System.out.println(runFuture.join());
       //supplyAsync的future，有返回值
       System.out.println(supplyFuture.get());
       executor.shutdown(); // 线程池需要关闭
   }
*/
  /*  @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("先执行第一个CompletableFuture方法任务");
                    return "太丧了,取不到值!";
                }
        );

        CompletableFuture<Void> thenRunFuture = orgFuture.thenRun(() -> {
            System.out.println("接着执行第二个任务");
        });
        System.out.println(thenRunFuture.get());
    }*/
  /*  @SneakyThrows
    public static void main(String[] args) {

        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("原始CompletableFuture方法任务");
                    return "奥特曼";
                }
        );

        CompletableFuture<Void> thenAcceptFuture = orgFuture.thenAccept((a) -> {
            if ("奥特曼".equals(a)) {
                System.out.println("大战小怪兽");
            }

            System.out.println("奥利给");
        });

        System.out.println(thenAcceptFuture.get());
    }
*/ /* @SneakyThrows
    public static void main(String[] args) {

        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("原始CompletableFuture方法任务");
                    return "奥特曼";
                }
        );
        CompletableFuture<String> thenApplyFuture = orgFuture.thenApply((a) -> {
            if ("奥特曼".equals(a)) {
                return "奥特曼大战小怪兽";
            }

            return "飞往第七星云";
        });
        System.out.println(thenApplyFuture.get());
    }*/
/*    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 100).exceptionally(e -> {
            System.out.println("出现异常了，返回默认值");
            return 110;
        });
        System.out.println(completableFuture.join());
    }*/
/*    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        }).exceptionally(e -> {
            System.out.println("出现异常了，返回默认值");
            return 110;
        });
        System.out.println(completableFuture.join());
    }*/
   /* @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("当前线程名称2：" + Thread.currentThread().getName());
                    int i= 1/0;
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "field";
                }
        );

        CompletableFuture<String> rstFuture = orgFuture.whenComplete((a, throwable) -> {
            System.out.println("当前线程名称1：" + Thread.currentThread().getName());
            System.out.println("上个任务执行完啦，还把" + a + "传过来");
            if ("field".equals(a)) {
                System.out.println("aaaaaaaaaaaaa");
            }
            System.out.println("bbbbbbbbbb");
            System.out.println(throwable);
        });
        System.out.println(rstFuture.get());
    }*/
   /* @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "第七星系奥特星球";
                }
        );

        CompletableFuture<String> rstFuture = orgFuture.handle((a, throwable) -> {
            System.out.println("上个任务执行完啦，还把" + a + "传过来");
            if ("第七星系奥特星球".equals(a)) {
                System.out.println("666");
                return "光 大古";
            }
            System.out.println("啛啛喳喳错错错错");
            return null;
        });
        System.out.println(rstFuture.get());
    }*/
   /* @SneakyThrows
    public static void main(String[] args) {

        CompletableFuture<Void> a = CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("a我执行完了");
        });
        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> System.out.println("b我也执行完了"));
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a, b).whenComplete((m,k)-> System.out.println("finish"));
        anyOfFuture.join();
    }*/
    @SneakyThrows
    public static void main(String[] args) {

        CompletableFuture<String> f = CompletableFuture.completedFuture("第一个任务");
        //第二个异步任务
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "第二个任务", executor)
                .thenComposeAsync(data -> {
                    System.out.println(data);
                    return f; //使用第一个任务作为返回
                }, executor);
        System.out.println(future.join());
        executor.shutdown();
    }
}
