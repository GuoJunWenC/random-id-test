package org.example.api.limit;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SlidingWindowRateLimiter {
    private final int windowSize;             // 窗口大小（秒）
    private final int limitPerWindow;         // 每个窗口的请求限制数量
    private final Map<Integer, Window> shardMap;  // 分片和窗口的映射

    public SlidingWindowRateLimiter(int windowSize, int limitPerWindow) {
        this.windowSize = windowSize;
        this.limitPerWindow = limitPerWindow;
        this.shardMap = new HashMap<>();
    }

    public boolean allowRequest(int shardId) {
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // 获取当前分片对应的窗口
        Window window = shardMap.computeIfAbsent(shardId, k -> new Window(windowSize));

        // 移除过期的窗口
        window.removeExpiredWindows(currentTimestamp - windowSize);

        if (window.getNumRequests() < limitPerWindow) {
            window.addRequest(currentTimestamp);
            return true;
        }

        return false;
    }

    private static class Window {
        private final Map<Long, Integer> timestampMap;  // 时间戳和请求数的映射
        private int count;                              // 窗口内请求数量

        public Window(int windowSize) {
            this.timestampMap = new HashMap<>(windowSize);
            this.count = 0;
        }

        public void removeExpiredWindows(long expirationTimestamp) {
            timestampMap.entrySet().removeIf(entry -> entry.getKey() <= expirationTimestamp);
            count = timestampMap.values().stream().mapToInt(Integer::intValue).sum();
        }

        public void addRequest(long timestamp) {
            timestampMap.put(timestamp, timestampMap.getOrDefault(timestamp, 0) + 1);
            count++;
        }

        public int getNumRequests() {
            return count;
        }
    }

    public static void main(String[] args) {
        int windowSize = 60;
        int limitPerWindow = 5;
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(windowSize, limitPerWindow);

        // 模拟连续的请求
        for (int i = 1; i <= 30; i++) {
            int shardId = (i - 1) % 2;   // 假设有两个分片，分片下标从0开始
            boolean allowed = rateLimiter.allowRequest(shardId);
            System.out.println("Request " + i + ": Shard " + shardId + " - " + (allowed ? "Allowed" : "Rejected"));
        }
    }
}
