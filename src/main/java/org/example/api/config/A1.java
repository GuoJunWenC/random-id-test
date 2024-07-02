package org.example.api.config;

public class A1 extends Guli {
    @Override
    protected String getRoadSectionsResult() {
        return "A1";
    }
    public static void main(String[] args) {
        WaitingExample waitingExample = new WaitingExample();
        for (int i = 0; i < 10; i++) {
            waitingExample.producerThread();
            waitingExample.consumerThread();
            waitingExample.consumerThread();
        }


    }
    public static class WaitingExample {
        private final Object lock = new Object();
        private boolean conditionMet = false;

        public void producerThread() {
            synchronized (lock) {
                // 生产数据或达到某种条件
                conditionMet = true;
                lock.notify(); // 唤醒等待的消费者线程
                System.out.println("生产");
                // ... 其他操作 ...
            }
        }

        public void consumerThread() {
            synchronized (lock) {
                while (!conditionMet) {
                    try {
                        lock.wait(); // 条件未满足，进入等待
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        // 处理中断异常
                    }
                }
                // 条件满足，执行消费操作
                // ...

                // 消费完成后可选择重置条件
                conditionMet = false;
                System.out.println("消费");
            }
        }
    }

}
