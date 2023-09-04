package org.example.api.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appLog")
public class AppLogTestController {

    @PostMapping("/test")
    public B test(@RequestParam("name") String name, @RequestBody A a) {
        return new B(name, a.age);
    }
    @Data
    public static class A {
        private Integer age;
    }
    @Data
    @AllArgsConstructor
    public static class B {
        private String name;
        private Integer age;
    }
}