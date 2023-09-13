package com.base.test.algorithm;

public class test {
    class Solution {
        String reverseDigit(int n) {
            int x=Math.abs(n);
            long result = x; int count=1;//先将x赋值给result，用count计数以便后续数组操作
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
        public boolean isPalindrome(int x) {

            return String.valueOf(x).equals(reverseDigit(x));
        }


    }
}
