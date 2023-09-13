package com.base.test.algorithm;

import java.util.Scanner;

/**
 * 罗马数字转整数
 * 思路：正常累加,如果当前值比右边小，变为负号
 * @author nnc
 * @date 2023/9/1 9:36
 */
public class RomanNumeralToInteger {
     static int romanToInt(String s) {
         int sum=0;
        int preNum=getValue(s.charAt(0));
        for (int i=1;i<s.length();i++){
            int num=getValue(s.charAt(i));
            if (preNum<num){
                sum-=preNum;
            }else {
                sum+=preNum;
            }
            preNum=num;
        }
        sum+=preNum;//最后一位必为正

        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        System.out.println(romanToInt(a));
    }

    static int getValue(char s) {
        switch (s) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }

    }
}
