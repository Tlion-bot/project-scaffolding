package com.base.test.java.timeutils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class TIME {

    public static List<LocalTime> getTimeIntervals(LocalTime startTime, LocalTime endTime) {
        List<LocalTime> timeIntervals = new ArrayList<>();

        // 处理开始时间大于结束时间（即跨越午夜）的情况
        LocalDateTime startDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), startTime);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), endTime);

        // 如果结束时间小于开始时间，则认为是跨天的情况
        if (endDateTime.isBefore(startDateTime)) {


            // 计算到当天23:59:59的时间点
            LocalDateTime endOfDay = startDateTime.withHour(23).withMinute(59).withSecond(59);

            // 添加从开始时间到当天结束的时间点
            for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(endOfDay); dateTime = dateTime.plusMinutes(5)) {
                timeIntervals.add(dateTime.toLocalTime());
            }

            // 重置开始时间为第二天的00:00:00
            startDateTime = startDateTime.plusDays(1);

            // 添加从第二天的00:00:00到结束时间的时间点
            for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(endDateTime); dateTime = dateTime.plusMinutes(5)) {
                timeIntervals.add(dateTime.toLocalTime());
            }

            startDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(1), LocalTime.of(0, 0));
            endDateTime = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), endTime);

            // 如果没有跨越午夜，则直接添加从开始到结束的时间点
            for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(endDateTime); dateTime = dateTime.plusMinutes(5)) {
                timeIntervals.add(dateTime.toLocalTime());
            }

        } else {

            // 如果没有跨越午夜，则直接添加从开始到结束的时间点
            for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(endDateTime); dateTime = dateTime.plusMinutes(5)) {
                timeIntervals.add(dateTime.toLocalTime());
            }
            // 如果结束时间也应该是结果的一部分（且它是5分钟的倍数）
            if (endDateTime.getMinute() % 5 == 0 && endDateTime.getSecond() == 0) {
                timeIntervals.add(endDateTime.toLocalTime());
            }
            if (endDateTime.toLocalTime().equals( LocalTime.of(0, 0))) {
                timeIntervals.add(LocalTime.of(0, 0));
            }
        }
        return timeIntervals;
    }

    public static void main(String[] args) {
        LocalTime startTime = LocalTime.of(23, 0);
        LocalTime endTime = LocalTime.of(14, 0); // 假设这是次日的凌晨1点

        List<LocalTime> intervals = getTimeIntervals(startTime, endTime);

        // 打印结果
        for (LocalTime time : intervals) {
            System.out.println(time);
        }


    }
}
