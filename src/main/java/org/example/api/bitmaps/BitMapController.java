package org.example.api.bitmaps;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/bitMap")
public class BitMapController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录
     *
     * @param userId 用户id
     */
    @RequestMapping("/logIn")
    public void logIn(Integer userId) {
        // 写入redis setbit key offset 1
        stringRedisTemplate.opsForValue().setBit("USER_LOG_IN_KEY", userId, Boolean.TRUE);
    }

    /**
     * 查询用户是否登录
     *
     * @param userId 用户id
     */
    @RequestMapping("/checkLogIn")
    public Boolean checkLogIn(Integer userId) {
        return stringRedisTemplate.opsForValue().getBit("USER_LOG_IN_KEY", userId);
    }

    public static void main(String[] args) {
        // 创建Jedis实例
        Jedis jedis = new Jedis("8.142.153.195");

        // 执行BITOP指令
        Long result = jedis.bitop(BitOP.AND, "USER_SIGN_KEY_STATISTICS", "USER_SIGN_KEYS:2023-10-12",
                "USER_SIGN_KEYS:2023-10-13", "USER_SIGN_KEYS:2023-10-14", "USER_SIGN_KEYS:2023-10-15", "USER_SIGN_KEYS:2023-10-16", "USER_SIGN_KEYS:2023-10-17",
                "USER_SIGN_KEYS:2023-10-18");
        System.out.println("Result: " + result);
        Long user_sign_key_statistics = jedis.bitcount("USER_SIGN_KEY_STATISTICS");
        System.out.println(user_sign_key_statistics);
        // 关闭Jedis连接
        jedis.close();
    }

    /**
     * 连续签到用户总数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    @RequestMapping("/continuousLogIn")
    public Long continuousLogIn(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> allBetweenDate = getAllBetweenDate(startDate, endDate);
        List<byte[]> bytes = allBetweenDate.stream().map(s -> ("USER_SIGN_KEYS:" + s).getBytes()).collect(Collectors.toList());
        stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitOp(RedisStringCommands.BitOperation.AND, "collect".getBytes(),
                bytes.get(0), bytes.get(1), bytes.get(2), bytes.get(3), bytes.get(4), bytes.get(5), bytes.get(6)
        ));

        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount("collect".getBytes()));
    }

    private List<LocalDate> getAllBetweenDate(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(startDate::plusDays).collect(Collectors.toList());
    }

    /**
     * 签到
     *
     * @param userId 用户id
     * @param date   日期
     */
    @RequestMapping("/sign")
    public void sign(Integer userId, LocalDate date) {
        String keySuffix = date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEYS:" + userId + keySuffix;
        stringRedisTemplate.opsForValue().setBit(key, date.getDayOfMonth(), true);
    }

    /**
     * 查询当天是否有签到
     *
     * @param userId 用户id
     * @param date   日期
     */
    @RequestMapping("/checkSign")
    public Boolean checkSign(Integer userId, LocalDate date) {
        String keySuffix = date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEY:" + userId + keySuffix;
        int dayOfMonth = date.getDayOfMonth();
        return stringRedisTemplate.opsForValue().getBit(key, dayOfMonth - 1);
    }

    /**
     * 获取本月累计签到数
     *
     * @param userId 用户id
     * @param date   日期
     */
    @RequestMapping("/getSumSignCount")
    public Long getSumSignCount(Integer userId, LocalDate date) {
        String keySuffix = date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEY:" + userId + keySuffix;
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    /**
     * 获取本月首次签到日期
     *
     * @param userId 用户id
     * @param date   日期
     */
    @RequestMapping("/getFirstSign")
    public Long getFirstSign(Integer userId, LocalDate date) {
        String keySuffix = date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEY:" + userId + keySuffix;
        Long execute = stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitPos(key.getBytes(), Boolean.TRUE));
        if (Objects.nonNull(execute) && execute > -1) {
            return execute + 1;
        }
        return execute;
    }

    /**
     * 获取今天之前的的连续签到次数
     *
     * @return Integer
     */
    @GetMapping("/signCount")
    public Integer signCount(Integer userId) {
        //1. 获取日期
        LocalDateTime now = LocalDateTime.now();
        //2. 拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEYS:" + userId + keySuffix;
        //3. 获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //4. 获取本月截至今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD USER_SIGN_KEYS:100:202310 GET u18 0
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        //没有任务签到结果
        if (CollectionUtils.isEmpty(result)) {
            return 0;
        }
        //11 -> 1011
        Long num = result.get(0);
        if (num == null || num == 0) {
            return 0;
        }
        //5. 循环遍历
        int count = 0;
        while (true) {
            //6 让这个数字与1 做与运算，得到数字的最后一个bit位 判断这个数字是否为0
            if ((num & 1) == 0) {
                //如果为0，签到结束
                break;
            } else {
                count++;
            }
            //右移一位
            num >>>= 1;
        }
        return count;
    }
}
