package org.example.api.functiontest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.example.api.enums.ErrorCodeEnum;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@RequestMapping("/functionTarget")
@RestController
public class FunctionTarget {
    @Autowired
    private SysLogMapper sysLogMapper;
    @GetMapping("/test")
    public void test() {
        ensureColumnValueValid(11L, SysLog::getId, sysLogMapper::selectOne, "用户ID无效");
        // 当用户角色不是 “普通用户” 时抛异常
        validateColumnValueMatchesExpected(SysLog::getMethod, "PUT", SysLog::getId, 11L, sysLogMapper::selectOne, "用户角色不是普通用户，无法升级为管理员！");
        // 假设 OrderStatusEnum 枚举了所有可能的订单状态，cancelableStatuses 包含可取消的状态
        List<String> cancelableStatuses = Arrays.asList(ErrorCodeEnum.ERROR.getMessage(), ErrorCodeEnum.OK.getMessage());

    // 验证订单状态是否在可取消状态列表内
        validateColumnValueInExpectedList(SysLog::getMethod, cancelableStatuses, SysLog::getId, 111L, sysLogMapper::selectOne, "订单当前状态不允许取消！");
    }

    public static void main(String[] args) {
        int i = 2;
        String s1 = "]";
        String s ="["+"2"+ "]";

        System.out.println(s);
        System.out.println(Integer.parseInt(s.replace("[","").replace("]","")));
    }
    /**
     * 确认数据库字段值有效（通用）
     *
     * @param <V>             待验证值的类型
     * @param valueToCheck    待验证的值
     * @param columnExtractor 实体类属性提取函数
     * @param queryExecutor   单条数据查询执行器
     * @param errorMessage    异常提示信息模板
     */
    public static <T, R, V> void ensureColumnValueValid(V valueToCheck, SFunction<T, R> columnExtractor, SFunction<LambdaQueryWrapper<T>, T> queryExecutor, String errorMessage) {
        if (valueToCheck == null) return;

        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(columnExtractor);
        wrapper.eq(columnExtractor, valueToCheck);
        wrapper.last("LIMIT 1");

        T entity = queryExecutor.apply(wrapper);
        R columnValue = columnExtractor.apply(entity);
        if (entity == null || columnValue == null)
            throw new DataValidationException(String.format(errorMessage, valueToCheck));
    }

    /**
     * 验证查询结果中指定列的值是否与预期值匹配
     *
     * @param <T>             实体类型
     * @param <R>             目标列值的类型
     * @param <C>             查询条件列值的类型
     * @param targetColumn    目标列的提取函数，用于获取想要验证的列值
     * @param expectedValue   期望的列值
     * @param conditionColumn 条件列的提取函数，用于设置查询条件
     * @param conditionValue  条件列对应的值
     * @param queryMethod     执行查询的方法引用，返回单个实体对象
     * @param errorMessage    验证失败时抛出异常的错误信息模板
     * @throws RuntimeException 当查询结果中目标列的值与预期值不匹配时抛出异常
     */
    public static <T, R, C> void validateColumnValueMatchesExpected(
            SFunction<T, R> targetColumn, R expectedValue,
            SFunction<T, C> conditionColumn, C conditionValue,
            SFunction<LambdaQueryWrapper<T>, T> queryMethod,
            String errorMessage) {

        // 创建查询包装器，选择目标列并设置查询条件
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(targetColumn);
        wrapper.eq(conditionColumn, conditionValue);

        // 执行查询方法
        T one = queryMethod.apply(wrapper);
        // 如果查询结果为空，则直接返回，视为验证通过（或忽略）
        if (one == null) return;

        // 获取查询结果中目标列的实际值
        R actualValue = targetColumn.apply(one);

        // 比较实际值与预期值是否匹配，这里假设notMatch是一个自定义方法用于比较不匹配情况
        boolean doesNotMatch = notMatch(actualValue, expectedValue);
        if (doesNotMatch) {
            // 若不匹配，则根据错误信息模板抛出异常
            throw new RuntimeException(String.format(errorMessage, expectedValue, actualValue));
        }
    }

    // 假设的辅助方法，用于比较值是否不匹配，根据实际需要实现
    private static <R> boolean notMatch(R actual, R expected) {
        // 示例简单实现为不相等判断，实际情况可能更复杂
        return !Objects.equals(actual, expected);
    }

    /**
     * 验证查询结果中指定列的值是否位于预期值列表内
     *
     * @param <T>               实体类型
     * @param <R>               目标列值的类型
     * @param <C>               查询条件列值的类型
     * @param targetColumn      目标列的提取函数，用于获取想要验证的列值
     * @param expectedValueList 期望值的列表
     * @param conditionColumn   条件列的提取函数，用于设置查询条件
     * @param conditionValue    条件列对应的值
     * @param queryMethod       执行查询的方法引用，返回单个实体对象
     * @param errorMessage      验证失败时抛出异常的错误信息模板
     * @throws RuntimeException 当查询结果中目标列的值不在预期值列表内时抛出异常
     */
    public static <T, R, C> void validateColumnValueInExpectedList(SFunction<T, R> targetColumn, List<R> expectedValueList, SFunction<T, C> conditionColumn, C conditionValue, SFunction<LambdaQueryWrapper<T>, T> queryMethod, String errorMessage) {

        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(targetColumn);
        wrapper.eq(conditionColumn, conditionValue);

        T one = queryMethod.apply(wrapper);
        if (one == null) return;

        R actualValue = targetColumn.apply(one);
        if (actualValue == null) throw new RuntimeException("列查询结果为空");

        if (!expectedValueList.contains(actualValue)) {
            throw new RuntimeException(errorMessage);
        }
    }
}
