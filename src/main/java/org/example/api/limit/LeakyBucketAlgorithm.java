package org.example.api.limit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeakyBucketAlgorithm {
    private final int capacity;  // 漏桶容量
    private final int rate;      // 漏出速率
    private int water;     // 当前水量
    private long lastLeakTime;  // 上次漏水时间

    public LeakyBucketAlgorithm(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.water = 0;
        this.lastLeakTime = System.currentTimeMillis();
    }

    public synchronized boolean addWater(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("添加的水滴数量不能为负数");
        }
        long currentTime = System.currentTimeMillis();
        //当前时间和最后一次漏水时间差值
        long elapsedTime = currentTime - lastLeakTime;
        //漏水数量
        int leakedWater = (int) (elapsedTime / 1000 * rate);
        //获取水桶现有容量
        water = Math.max(0, water - leakedWater);
        //水滴数量和水桶容量相等,得等待漏水.
        if (water != capacity) {
            lastLeakTime = System.currentTimeMillis();
        }

        if (water + count <= capacity) {
            water += count;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        LeakyBucketAlgorithm bucket = new LeakyBucketAlgorithm(10, 2);

        // 模拟测试：添加300个水滴
        for (int i = 1; i <= 50; i++) {
            boolean result = bucket.addWater(1);
            if (!result) {
                System.out.println("失败：" + i);
            } else {
                System.out.println("成功：" + i);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100); // 模拟请求间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
