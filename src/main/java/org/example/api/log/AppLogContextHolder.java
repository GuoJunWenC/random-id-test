package org.example.api.log;

public class AppLogContextHolder {

    private static final ThreadLocal<AppLog> TL = ThreadLocal.withInitial(AppLog::new);

    public static AppLog get() {
        // 获取线程上下文的日志对象
        return TL.get();
    }

    public static void remove() {
        // 移除线程上下文的日志对象
        TL.remove();
    }
}
