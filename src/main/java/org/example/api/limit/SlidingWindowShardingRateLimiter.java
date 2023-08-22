package org.example.api.limit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowShardingRateLimiter {
    private final int windowSize;       // 窗口大小（秒）
    private final int limitPerShard;    // 每个分片的阈值
    private final int numShards;        // 分片数量
    private Map<Integer, Shard> shards; // 分片映射表

    public SlidingWindowShardingRateLimiter(int windowSize, int limitPerShard, int numShards) {
        this.windowSize = windowSize;
        this.limitPerShard = limitPerShard;
        this.numShards = numShards;
        this.shards = new HashMap<>();

        // 初始化分片映射表
        for (int i = 0; i < numShards; i++) {
            shards.put(i, new Shard());
        }
    }

    public boolean allowRequest(int shardId) {
        if (!shards.containsKey(shardId)) {
            throw new IllegalArgumentException("Invalid shard ID");
        }

        Shard shard = shards.get(shardId);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long windowStart = currentTimestamp - windowSize-1; // 加1纠正计算错误

        // 移除过期的时间戳
        shard.removeExpiredTimestamps(windowStart);
        // 添加当前时间戳到队列中
        shard.addTimestamp(currentTimestamp);
        // 判断请求数量是否超过阈值
        return shard.getNumRequests() < limitPerShard;
    }

    private class Shard {
        private Queue<Long> timestampQueue; // 时间戳队列

        public Shard() {
            this.timestampQueue = new LinkedList<Long>();
        }
        public void addTimestamp(long timestamp) {
            timestampQueue.add(timestamp);
        }
        public void removeExpiredTimestamps(long windowStart) {
            while (!timestampQueue.isEmpty() && timestampQueue.peek() <= windowStart) { // 修改条件
                timestampQueue.poll();
            }
        }

        public int getNumRequests() {
            return timestampQueue.size();
        }
    }

    public static void main(String[] args) {
        SlidingWindowShardingRateLimiter limiter = new SlidingWindowShardingRateLimiter(60, 10, 5);

        // 模拟连续的请求
        for (int i = 0; i <= 60; i++) {
            int shardId = i % 5; // 修改取模运算，从0开始编号
            // 根据请求序号确定分片ID
            boolean allowed = limiter.allowRequest(shardId);
            System.out.println("Request " + i + " (Shard " + shardId + "): " + (allowed ? "Allowed" : "Rejected"));
        }
    }
}
