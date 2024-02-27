package com.base.test.java.projectTest;

import com.base.test.java.dao.Years;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class qiunianyue2 {


    public static void main(String[] args) {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 创建列表来存储Years对象
        List<Years> yearsList = new ArrayList<>();

        // 循环近一年的每个月，包括当前月
        for (int i = 0; i < 12; i++) {
            // 计算当前月的日期，如果i为0，则使用今天的日期
            LocalDate month = today.minusMonths(i);

            // 创建Years对象并设置年份和月份
            Years years = new Years(month.getYear(), month.getMonthValue());

            // 将Years对象添加到列表中
            yearsList.add(years);
        }

        // 打印结果
        for (Years year : yearsList) {
            System.out.println(year);
        }
    }
}
