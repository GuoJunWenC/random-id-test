package org.example.api.joinrequest;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

@Service
public class UserWrapBatchService {
    @Resource
    private UserService userService;

    /**
     * 最大任务数
     **/
    public static int MAX_TASK_NUM = 100;


    /**
     * 请求类,code为查询的共同特征,例如查询商品,通过不同id的来区分
     * CompletableFuture将处理结果返回
     */
    public class Request {
        // 请求id 唯一
        String requestId;
        // 参数
        Long userId;
        //TODO Java 8 的 CompletableFuture 并没有 timeout 机制
        CompletableFuture<Users> completableFuture;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public CompletableFuture getCompletableFuture() {
            return completableFuture;
        }

        public void setCompletableFuture(CompletableFuture completableFuture) {
            this.completableFuture = completableFuture;
        }
    }

    /*
    LinkedBlockingQueue是一个阻塞的队列,内部采用链表的结果,通过两个ReenTrantLock来保证线程安全
    LinkedBlockingQueue与ArrayBlockingQueue的区别
    ArrayBlockingQueue默认指定了长度,而LinkedBlockingQueue的默认长度是Integer.MAX_VALUE,也就是无界队列,在移除的速度小于添加的速度时，容易造成OOM。
    ArrayBlockingQueue的存储容器是数组,而LinkedBlockingQueue是存储容器是链表
    两者的实现队列添加或移除的锁不一样，ArrayBlockingQueue实现的队列中的锁是没有分离的，即添加操作和移除操作采用的同一个ReenterLock锁，
    而LinkedBlockingQueue实现的队列中的锁是分离的，其添加采用的是putLock，移除采用的则是takeLock，这样能大大提高队列的吞吐量，
    也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。
     */
    private final Queue<Request> queue = new LinkedBlockingQueue();

    @PostConstruct
    public void init() {
        //定时任务线程池,创建一个支持定时、周期性或延时任务的限定线程数目(这里传入的是1)的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int size = queue.size();
            //如果队列没数据,表示这段时间没有请求,直接返回
            if (size == 0) {
                return;
            }
            List<Request> list = new ArrayList<>();
            System.out.println("合并了 [" + size + "] 个请求");
            //将队列的请求消费到一个集合保存
            for (int i = 0; i < size; i++) {
                // 后面的SQL语句是有长度限制的，所以还要做限制每次批量的数量,超过最大任务数，等下次执行
                if (i < MAX_TASK_NUM) {
                    list.add(queue.poll());
                }
            }
            //拿到我们需要去数据库查询的特征,保存为集合
            List<Request> userReqs = new ArrayList<>(list);
            //将参数传入service处理, 这里是本地服务，也可以把userService 看成RPC之类的远程调用
            Map<String, Users> response = userService.queryUserByIdBatch(userReqs);
            //将处理结果返回各自的请求
            for (Request request : list) {
                Users result = response.get(request.requestId);
                request.completableFuture.complete(result);    //completableFuture.complete方法完成赋值,这一步执行完毕,下面future.get()阻塞的请求可以继续执行了
            }
        }, 100, 10, TimeUnit.MILLISECONDS);
        //scheduleAtFixedRate是周期性执行 schedule是延迟执行 initialDelay是初始延迟 period是周期间隔 后面是单位
        //这里我写的是 初始化后100毫秒后执行，周期性执行10毫秒执行一次
    }

    public Users queryUser(Long userId) {
        Request request = new Request();
        // 这里用UUID做请求id
        request.requestId = UUID.randomUUID().toString().replace("-", "");
        request.userId = userId;
        CompletableFuture<Users> future = new CompletableFuture<>();
        request.completableFuture = future;
        //将对象传入队列
        queue.offer(request);
        //如果这时候没完成赋值,那么就会阻塞,直到能够拿到值
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}