package org.example.api.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class LogAspect  {
    public void beforeDeleteUser() {
        System.out.println("Before Add User...");
    }

    public void afterDeleteUser() {
        System.out.println("After Delete User...");
    }
    public void aroundDeleteUser() {
        System.out.println("Around Delete User...");
    }
    public void afterReturnAddUser(String userName) {
        System.out.println("After Return Delete User..."+userName);
    }
    public void afterThrowingDeleteUser(Exception ex) {
        System.out.println("After  ThrowingDelete User...");
    }

    // 支持的所有日期格式
    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy/M/d"),    // 处理 2024/4/23 格式
            DateTimeFormatter.ofPattern("yyyy/M/dd"),    // 处理 2024/4/23 格式
            DateTimeFormatter.ofPattern("yyyy/MM/d"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy年M月d日"), // 处理 2024年4月23日 格式
            DateTimeFormatter.ofPattern("yyyy年MM月d日"),  // 处理 2024年4月23日 格式
            DateTimeFormatter.ofPattern("yyyy年M月dd日"),  // 处理 2024年4月23日 格式
            DateTimeFormatter.ofPattern("yyyy年MM月dd日") // 处理 2024年4月23日 格式

    );

    public static String convertDate(String dateStr) {
        // 去除可能存在的"日"后缀
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                return date.format(DateTimeFormatter.ISO_DATE); // yyyy-MM-dd
            } catch (DateTimeParseException ignored) {
                // 尝试下一个格式
            }
        }
        throw new IllegalArgumentException("无法解析的日期格式: " + dateStr);
    }

    public static void main(String[] args) {
        System.out.println(convertDate("2024/4/23"));    // 2024-04-23
        System.out.println(convertDate("2024/4/2"));    // 2024-04-23

        System.out.println(convertDate("2024年4月23日"));   // 2024-04-23
        System.out.println(convertDate("2024/12/3"));    // 2024-12-03
        System.out.println(convertDate("2024/12/13"));    // 2024-12-03
        System.out.println(convertDate("2024年1月5日")); // 2024-10-05

        System.out.println(convertDate("2024年10月5日")); // 2024-10-05
        System.out.println(convertDate("2024年10月15日")); // 2024-10-05
    }

}
