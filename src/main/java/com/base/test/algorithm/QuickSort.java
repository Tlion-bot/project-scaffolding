package com.base.test.algorithm;

public class QuickSort {


    /**
     * 快速排序思想
     * 快速排序所采用的思想是分治的思想。所谓分治，就是指以一个数为基准，将序列中的其他数往它两边“扔”。
     * 以从小到大排序为例，比它小的都“扔”到它的左边，比它大的都“扔”到它的右边，然后左右两边再分别重复这个操作，不停地分，直至分到每一个分区的基准数的左边或者右边都只剩一个数为止。这时排序也就完成了。
     *
     * @author nnc
     * @date 2023/8/31 16:04
     */


    public static void main(String[] a) {

        int[] b = {1, 6, 8, 7, 3, 5, 16, 4, 8, 36, 13, 44};

        quickSort(b);

        for (int i : b) {

            System.out.print(i + " ");

        }

    }

    /**
     * 快速排序
     *
     * @param array
     */

    public static void quickSort(int[] array) {

        int len;

        if (array == null || (len = array.length) == 0 || len == 1) {

            return;

        }

        sort(array, 0, len - 1);

    }

    /**
     * 快排核心算法，递归实现
     *
     * @param array
     * @param left
     * @param right
     */

    public static void sort(int[] array, int left, int right) {

        if (left > right) {

            return;

        }

// base中存放基准数

        int base = array[left];

        int i = left, j = right;

        while (i != j) {

// 顺序很重要，先从右边开始往左找，直到找到比base值小的数

            while (array[j] >= base && i < j) {

                j--;

            }

// 再从左往右边找，直到找到比base值大的数

            while (array[i] <= base && i < j) {

                i++;

            }

// 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置

            if (i < j) {

                int tmp = array[i];

                array[i] = array[j];

                array[j] = tmp;

            }

        }

// 将基准数放到中间的位置（基准数归位）

        array[left] = array[i];

        array[i] = base;

// 递归，继续向基准的左右两边执行和上面同样的操作

        // i的索引处为上面已确定好的基准值的位置，无需再处理

        sort(array, left, i - 1);

        sort(array, i + 1, right);

    }

}

