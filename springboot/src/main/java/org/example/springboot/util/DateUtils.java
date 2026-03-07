package org.example.springboot.util;

import java.time.LocalDate;

public class DateUtils {
    /**
     * 计算上个月的第一天。
     * @return 上个月第一天的LocalDate对象。
     */
    public static LocalDate getLastMonthFirstDay() {
        LocalDate today = LocalDate.now(); // 获取当前日期
        int currentMonth = today.getMonthValue(); // 获取当前月份
        int currentYear = today.getYear(); // 获取当前年份

        // 如果当前月份是1月，则上个月是去年的12月
        // 获取上个月的第一天
        return LocalDate.now()
                .withDayOfMonth(1)           // 先确保在当前月的第一天
                .minusMonths(1);              // 再减去一个月，得到上个月的第一天
    }
}
