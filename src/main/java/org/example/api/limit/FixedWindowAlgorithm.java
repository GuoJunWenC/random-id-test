package org.example.api.limit;

public class FixedWindowAlgorithm {
    private final int windowSize;  // 时间窗口的长度，单位为秒
    private final int limit;  // 请求限制数量
    private int count;  // 计数器
    private long startTime;  // 当前时间窗口的起始时间

    public FixedWindowAlgorithm(int windowSize, int limit) {
        this.windowSize = windowSize;
        this.limit = limit;
        this.count = 0;
        this.startTime = System.currentTimeMillis() / 1000;  // 获取当前时间戳（秒）
    }

    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis() / 1000;  // 获取当前时间戳（秒）

            if (currentTime - startTime >= windowSize) {
            // 窗口滑动，重置计数器和起始时间
            count = 0;
            startTime = currentTime;
        }

        if (count < limit) {
            count++;
            return true;  // 允许请求通过
        } else {
            return false;  // 请求超过限制，拒绝请求
        }
    }
}
