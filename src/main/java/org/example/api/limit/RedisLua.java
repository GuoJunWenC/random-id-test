package org.example.api.limit;

import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RedisLua {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            boolean accQuire = accQuire();
            System.out.println(accQuire);
        }
    }

    @SneakyThrows
    public static boolean accQuire() {
        Jedis jedis = new Jedis("8.141.253.195");
        File luaFile = new File(Objects.requireNonNull(RedisLua.class.getResource("/")).toURI().getPath() + "limit.lua");
        String luaScript = new String(Files.readAllBytes(Paths.get(luaFile.getPath())));

        String key = "ip:" + System.currentTimeMillis() / 1000; // 当前秒
        String limit = "5"; // 最大限制
        List<String> keys = new ArrayList<String>();
        keys.add(key);
        List<String> args = new ArrayList<String>();
        args.add(limit);
        Long result = (Long) (jedis.eval(luaScript, keys, args)); // 执行lua脚本，传入参数
        return result != 0;
    }
}
