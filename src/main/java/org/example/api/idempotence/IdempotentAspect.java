package org.example.api.idempotence;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: 幂等性切面
 */
@Aspect
@Component
public class IdempotentAspect {
    @Resource
    private RedisIdempotentVerify redisIdempotentStorage;

    /**
     * 定义一个切面方法，用于匹配带有org.example.api.idempotence.Idempotent注解的方法。
     */
    @Pointcut("@annotation(org.example.api.idempotence.Idempotent)")
    public void idempotent() {
        // 方法体
    }


    /**
     * 该方法使用@Around注解，表示在指定的方法调用前执行该方法。
     *
     * @param joinPoint ProceedingJoinPoint类型的参数，表示当前正在执行的方法调用点
     * @return Object类型的值，表示执行方法的返回值
     * @throws Throwable 表示可能抛出任何类型的异常
     */
    @Around("idempotent()")
    public Object methodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前请求的HttpServletRequest对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的token
        String token = request.getHeader("token");
        // 获取目标方法的MethodSignature对象和Method对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取目标方法上的@Idempotent注解
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        // 获取注解中的name字段值
        String name = idempotent.name();
        // 获取注解中的type字段值，并根据类型实例化对象
        Class clazzType = idempotent.type();
        // 获取注解中的field字段值
        String field = idempotent.field();

        // 初始化value变量并赋值为token的值
        String value = token;

        // 创建实例对象
        Object object = clazzType.newInstance();
        // 获取方法参数值
        Map<String, Object> paramValue = AopUtils.getParamValue(joinPoint);
        // 判断对象是否是RequestData的实例
        if (object instanceof RequestData) {
            // 将name字段对应的参数值强制转换为RequestData类型，并赋值给idempotentEntity变量
            RequestData idempotentEntity = (RequestData) paramValue.get(name);
            // 通过idempotentEntity对象获取field字段的值，并将其转换为字符串类型赋值给value变量
            value = String.valueOf(AopUtils.getFieldValue(idempotentEntity, field));
        }
        // 判断token是否有效
        if (redisIdempotentStorage.validToken(token, value)) {
            // 执行目标方法并返回结果
            return joinPoint.proceed();
        }
        // 如果token无效，则返回"重复请求"字符串
        return "重复请求";
    }
}
