package com.base.test.java.sty;

import com.base.test.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * foreach遍历list 慎用add remove
 * 增强for循环中，集合遍历是通过iterator进行的，但是元素的add/remove却是直接使用的集合类自己的方法。
 * 这就导致iterator在遍历的时候，会发现有一个元素在自己不知不觉的情况下就被删除/添加了，就会抛出一个异常，用来提示用户，可能发生了并发修改。
 * @author nnc
 * @date 2023/8/31 10:19
 */

public class ForeachTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("1   ".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(list);
        System.out.println(DateUtils.dateTimeNow());
        System.out.println(DateUtils.dateTimeNow("YYYYMMddHHmmss"));
        System.out.println();






    }
}
