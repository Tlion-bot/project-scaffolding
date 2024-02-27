package com.base.test.java.projectTest;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class hebingweinian {
    public static void main(String[] args) {
        // Original data list
        List<PerformanceVO> allList = new ArrayList<>();
        PerformanceVO performance1 = new PerformanceVO();
        performance1.setYears("2023-12-25");
        performance1.setScore("A");
        allList.add(performance1);

        // Creating the second PerformanceVO object and adding it to allList
        PerformanceVO performance2 = new PerformanceVO();
        performance2.setYears("2023-06-25");
        performance2.setScore("B");
        allList.add(performance2);

        PerformanceVO performance3 = new PerformanceVO();
        performance3.setYears("2024-06-25"); // Corrected the variable name from jixiao1 to jixiao3
        performance3.setScore("A");
        allList.add(performance3);

        PerformanceVO performance4 = new PerformanceVO();
        performance4.setYears("2022-12-25");
        performance4.setScore("A");
        allList.add(performance4);


        List<jixiaoPrintVO> evaluationList = new ArrayList<>();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        for (PerformanceVO item : allList) {
            String yearsStr = item.getYears();
            String score =  item.getScore();

            try {

                int year = Integer.parseInt(dateFormat.format(dateFormat.parse(yearsStr)).substring(0, 4));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                YearMonth yearMonth = YearMonth.parse(yearsStr, formatter);

                if ((yearMonth.getMonthValue() ) >= Calendar.JULY+1) {

                    boolean found = false;
                    for (jixiaoPrintVO evaluation : evaluationList) {
                        if (evaluation.getYear() == year) {
                            evaluation.setSecondHalfYearScore(score);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // If there's no jixiaoPrintVO object for this year yet, create a new one
                        evaluationList.add(new jixiaoPrintVO(year, null, score));
                    }
                } else {
                    // First half-year evaluation
                    boolean found = false;
                    for (jixiaoPrintVO evaluation : evaluationList) {
                        if (evaluation.getYear() == year) {
                            evaluation.setFirstHalfYearScore(score);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {

                        evaluationList.add(new jixiaoPrintVO(year, score, null));
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        for (jixiaoPrintVO evaluation : evaluationList) {
            System.out.println("Year: " + evaluation.getYear() + ", First half-year score: " + evaluation.getFirstHalfYearScore() + ", Second half-year score: " + evaluation.getSecondHalfYearScore());
        }
    }

    @Data
    static class jixiaoPrintVO {
        private int year;
        private String firstHalfYearScore;
        private String secondHalfYearScore;

        public jixiaoPrintVO(int year, String firstHalfYearScore, String secondHalfYearScore) {
            this.year = year;
            this.firstHalfYearScore = firstHalfYearScore;
            this.secondHalfYearScore = secondHalfYearScore;
        }
    }

    @Data
    static class PerformanceVO {
        private String years;
        private String score;
    }
}
