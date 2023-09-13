package com.base.test.algorithm;

import java.util.Scanner;

/**
 * Determine whether the number of palindromes is correct
 *
 * @author nnc
 * @date 2023/8/31 14:30
 */
public class PalindromeNumber {


       static String reverseDigit(int n) {
            int x=Math.abs(n);
            int result = x; int count=1;//先将x赋值给result，用count计数以便后续数组操作
            while ((result /= 10) != 0) {
                count++;
            }
            int[] list = new int[count];
            for (int i = 0; i < count; i++) {
                list[i] = x % 10;
                result += list[i] * Math.pow(10, count - 1 - i);//使用幂运算，底数为10，指数为count-1-i
                x = x / 10;
            }
            if (n>0) {
                return String.valueOf(result);
            }else return String.valueOf(result)+"-";
        }
            public static void main(String[] args) {

                   System.out.println("Please input a int:");
                   Scanner sc = new Scanner(System.in);
                   int  n = sc.nextInt();
                    System.out.println(reverseDigit(n));
                    sc.close();
                }
        // public boolean isPalindrome(int x) {
        //
        // }
    }

