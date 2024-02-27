package com.base.test.java.projectTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class qiunianyue {


    public static void main(String[] args) {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 创建一个列表来存储结果
        List<String> result = new ArrayList<>();

        // 循环近一年的每个月
        for (int i = 0; i < 12; i++) {
            // 计算上一年的这个月的日期
            LocalDate lastYearMonth = today.minusMonths(i).minusMonths(1);

            // 格式化日期并添加到列表中
            result.add(lastYearMonth.format(formatter));
        }

        // 打印结果
        result.forEach(System.out::println);
    }
}

