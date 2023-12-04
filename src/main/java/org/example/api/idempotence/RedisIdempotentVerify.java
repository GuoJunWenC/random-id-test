package org.example.api.idempotence;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisIdempotentVerify implements IdempotentVerify {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 存入 Redis 的 Token 键的前缀
     */
    private static final String IDEMPOTENT_TOKEN_PREFIX = "idempotent_token:";

    /**
     * 保存  10分钟过期
     *
     * @param value        令牌的值
     * @param idempotentId 唯一标识
     */
    @Override
    public void save(String idempotentId, String value) {
        String token = IDEMPOTENT_TOKEN_PREFIX + idempotentId;
        if (StringUtils.isBlank(value)) {
            value = token;
        }
        redisTemplate.opsForValue().set(token, value, 10, TimeUnit.MINUTES);
    }

    /**
     * 删除 boolean
     *
     * @param idempotentId 唯一标识
     * @return boolean
     */
    @Override
    public boolean delete(String idempotentId) {
        String token = IDEMPOTENT_TOKEN_PREFIX + idempotentId;
        return Boolean.TRUE.equals(redisTemplate.delete(token));
    }

    /**
     * 验证 Token
     *
     * @param token 令牌
     * @param value 令牌的值
     * @return boolean
     */
    @Override
    public boolean validToken(String token, String value) {
        // 设置 Lua 脚本，其中 KEYS[1] 是 key，ARGV[1] 是 value
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        // 根据 Key 前缀拼接 Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;
        if (StringUtils.isBlank(value)) {
            value = key;
        }
        // 执行 Lua 脚本
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        // 根据返回结果判断是否成功成功匹配并删除 Redis 键值对，若果结果不为空和0，则验证通过
        if (Objects.nonNull(result) && result > 0L) {
            log.info("验证 token=[{}],key=[{}],value=[{}] 成功", token, key, value);
            return true;
        }
        log.info("验证 token=[{}],key=[{}],value=[{}] 失败", token, key, value);
        return false;
    }
}