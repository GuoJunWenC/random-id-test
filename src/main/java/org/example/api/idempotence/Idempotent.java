package org.example.api.idempotence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 幂等注解
 *
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 参数名，表示将从哪个参数中获取属性值。
     *
     * @return 参数名
     */
    String name() default "";

    /**
     * 属性，表示将获取哪个属性的值。
     *
     * @return 属性
     */
    String field() default "";

    /**
     * 参数类型
     *
     * @return 参数类型
     */
    Class type();
}
