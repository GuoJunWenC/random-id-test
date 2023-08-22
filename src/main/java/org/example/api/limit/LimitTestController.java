package org.example.api.limit;

import org.example.limit.aop.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/limitTest")
public class LimitTestController {

    @Limit(key = "getResult", permitsPerSecond = 1, timeout = 100, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/getResult")
    public String getResult() {
        return "ok";
    }
}
