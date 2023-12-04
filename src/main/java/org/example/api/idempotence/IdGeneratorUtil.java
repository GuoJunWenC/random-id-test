package org.example.api.idempotence;

import java.util.UUID;
/**
 * 用于生成唯一标识符的工具类
 *
 */
public class IdGeneratorUtil {
    /**
     * 用于生成唯一的标识符的工具类
     */
    private IdGeneratorUtil() {
        // 私有化构造方法，不允许直接实例化
    }

    /**
     * 生成一个唯一标识符
     *
     * @return 唯一标识符的字符串
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
