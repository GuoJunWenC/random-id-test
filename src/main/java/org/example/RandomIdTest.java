package org.example;

import lombok.RequiredArgsConstructor;
import org.example.api.service.User;
import org.example.api.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomIdTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userServiceImpl");
        User user = new User();
        user.setName("奥特曼");
        userService.addUser(user);
        userService.deleteUser(1);
    }
 /*   private  Integer i =0;
    private final WorkService workService;
    public ExecutorService er = new ThreadPoolExecutor(10, 20, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3000),
            new ThreadFactoryBuilder()
                    .setNameFormat("asyncTaskThreadPool-pool-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    @PostConstruct
    public void getRandomId() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000000);
        for (int j = 0; j < 1000000; j++) {
            er.execute(()-> {
                printI();
                countDownLatch.countDown();
                }
            );
            countDownLatch.countDown();
        }
        countDownLatch.await();
        System.out.println("最后值"+i);
    }

    public synchronized void printI() {

        i++;
    }*/
}
