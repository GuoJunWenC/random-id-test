package org.example.api.idempotence;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面工具类
 */
public class AopUtils {

    /**
     * 获取对象的字段值
     *
     * @param obj  目标对象
     * @param name 字段名
     * @return 字段值
     * @throws Exception 如果获取字段值失败
     */
    public static Object getFieldValue(Object obj, String name) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        Object object = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equalsIgnoreCase(name)) {
                object = field.get(obj);
                break;
            }
        }
        return object;
    }

    /**
     * 获取方法参数的值
     *
     * @param joinPoint 调用点
     * @return 参数值的映射，键为参数名，值为参数值
     */
    public static Map<String, Object> getParamValue(ProceedingJoinPoint joinPoint) {
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> param = new HashMap<>(paramNames.length);

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }
        return param;
    }
}
