package org.example.api.idempotence;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/idGenerator")
public class IdGeneratorController {

    @Resource
    private RedisIdempotentVerify redisIdempotentStorage;

    /**
     * 获取ID生成器的令牌
     *
     * @param value 令牌的值
     * @return 生成的令牌
     */
    @RequestMapping("/getIdGeneratorToken")
    public String getIdGeneratorToken(String value) {
        String generateId = IdGeneratorUtil.generateId();
        redisIdempotentStorage.save(generateId, value);
        return generateId;
    }
}
