package org.example.api.threads.sequentialprinting;

import jodd.util.ThreadUtil;
import org.assertj.core.util.DateUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ABCLockCondition {
    private final int max;
    // 用来指示当前应该打印的线程序号，0-A, 1-B, 2-C
    private int turn = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();

    public ABCLockCondition(int max) {
        this.max = max;
    }

    public void printA() {
        print("A", conditionA, conditionB);
    }

    public void printB() {
        print("B", conditionB, conditionC);
    }

    public void printC() {
        print("C", conditionC, conditionA);
    }

    private void print(String name, Condition currentCondition, Condition nextCondition) {
        for (int i = 0; i < max; i++) {
            lock.lock();
            try {
                // 等待直到轮到当前线程打印
                // turn 变量的值需要与线程要打印的字符相对应，例如，如果turn是0，且当前线程应该打印"A"，则条件满足。如果不满足，当前线程调用currentCondition.await()进入等待状态。
                while (!((turn == 0 && name.charAt(0) == 'A') || (turn == 1 && name.charAt(0) == 'B') || (turn == 2 && name.charAt(0) == 'C'))) {
                    currentCondition.await();
                }
                System.out.println(Thread.currentThread().getName() + " : " + name);
                // 更新打印轮次，并唤醒下一个线程
                turn = (turn + 1) % 3;
                nextCondition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    /*  public static void main(String[] args) {
          ABCLockCondition printer = new ABCLockCondition(10);
          Thread t1 = new Thread(printer::printA, "Thread A");
          Thread t2 = new Thread(printer::printB, "Thread B");
          Thread t3 = new Thread(printer::printC, "Thread C");

          t1.start();
          t2.start();
          t3.start();
      }*/
    public static void main(String[] args) {
        // T1
        CompletableFuture<Void> futureT1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1 is executing. Current time：" + DateUtil.now());
            // 模拟耗时操作
            ThreadUtil.sleep(1000);
        });
        // T2
        CompletableFuture<Void> futureT2 = CompletableFuture.runAsync(() -> {
            System.out.println("T2 is executing. Current time：" + DateUtil.now());
            ThreadUtil.sleep(1000);
        });

        // 使用allOf()方法合并T1和T2的CompletableFuture，等待它们都完成
        CompletableFuture<Void> bothCompleted = CompletableFuture.allOf(futureT1, futureT2);
        // 当T1和T2都完成后，执行T3
        bothCompleted.thenRunAsync(() -> System.out.println("T3 is executing after T1 and T2 have completed.Current time：" + DateUtil.now()));
        // 等待所有任务完成，验证效果
        ThreadUtil.sleep(3000);
    }
}
