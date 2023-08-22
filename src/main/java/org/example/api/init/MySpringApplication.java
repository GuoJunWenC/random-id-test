package org.example.api.init;

import lombok.NoArgsConstructor;
import org.example.OrgApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@NoArgsConstructor
@SpringBootApplication
public class MySpringApplication extends SpringApplication  {
    public MySpringApplication(Class<OrgApp> orgAppClass) {
        super(orgAppClass);
    }

    @Override
    protected void load(ApplicationContext context, Object[] sources) {
        // 在加载配置之前执行自定义逻辑
        doSomethingBeforeLoad();

        // 调用父类的 load() 方法加载配置
        super.load(context, sources);

        // 在加载配置之后执行自定义逻辑
        doSomethingAfterLoad();
    }
    private void doSomethingBeforeLoad() {
        // 执行启动之前的自定义初始化操作
        System.out.println("执行启动之前的自定义初始化操作");
    }

    private void doSomethingAfterLoad() {
        // 执行启动之后的自定义操作
        System.out.println("执行启动之后的自定义操作");
    }

}
