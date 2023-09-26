package org.example.api.joinrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserWrapBatchService userWrapBatchService;
    /***
     * 请求合并
     * */
    @RequestMapping("/merge")
    public Callable<Users> merge(Long userId) {
        return new Callable<Users>() {
            @Override
            public Users call() {
                return userWrapBatchService.queryUser(userId);
            }
        };
    }
}
