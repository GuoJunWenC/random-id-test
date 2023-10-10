package org.example.api.bitmaps;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/bitMap")
public class BitMapController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/sign")
    public void sign() {
        //1. 获取登录用户
        Long userId = IdWorker.getId();
        //2. 获取日期
        LocalDateTime now = LocalDateTime.now();
        //3. 拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEY" + userId % 3 + keySuffix;
        //4. 获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //5. 写入redis setbit key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
    }

    /**
     * 获取今天之前的的连续签到次数
     *
     * @return
     */
    @GetMapping("/signCount")
    public Integer signCount() {
        //1. 获取登录用户
        Long userId = IdWorker.getId();
        //2. 获取日期
        LocalDateTime now = LocalDateTime.now();
        //3. 拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_KEY" + userId % 3 + keySuffix;
        //4. 获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //5. 获取本月截至今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202301 GET u3 0
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        //没有任务签到结果
        if (result == null || result.isEmpty()) {
            return 0;
        }
        Long num = result.get(0);
        if (num == null || num == 0) {
            return 0;
        }
        //6. 循环遍历
        int count = 0;
        while (true) {
            //6.1 让这个数字与1 做与运算，得到数字的最后一个bit位 判断这个数字是否为0
            if ((num & 1) == 0) {
                //如果为0，签到结束
                break;
            } else {
                count++;
            }
            num >>>= 1;
        }
        return count;
    }
}
