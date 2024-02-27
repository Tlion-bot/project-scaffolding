package com.base.test.java.projectTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class test1 {
    public static void main(String[] args) {
        LocalDate joinWorkDate = LocalDate.of(2022, 1, 15);
        LocalDate currentDate = LocalDate.of(2023, 3, 15);

        long totalDays = ChronoUnit.MONTHS.between(joinWorkDate, currentDate);
        System.out.println("Total days between joinWorkDate and currentDate: " + totalDays);
    }
}
