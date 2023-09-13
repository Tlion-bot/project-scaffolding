package com.base.test.java;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetTest {
    public static void main(String[] args) {
        HashSet<String> HashSet = new HashSet<>();                        //创建一个HashSet对象,无法初始化大小
        HashSet.add("123");                                               //向HashSet链表中添加一个元素，可以有选择的返回一个boolean类型
        boolean add = HashSet.add("456");                                 //返回boolean类型
        int size = HashSet.size();                                        //返回元素个数
        boolean remove1 = HashSet.remove(0);                           //删除下标为0的元素（下标从0开始)，返回被删除的元素
        boolean remove = HashSet.remove("123");                        //删除链表中第一次出现的指定元素，返回boolean类型
        HashSet.clear();                                                  //清除链表中的元素
        boolean empty = HashSet.isEmpty();                                //判断链表是否为空
        boolean contains = HashSet.contains("123");                       //判断链表中是否有“123”这个元素，返回boolean类型
        HashSet.add("123");
        HashSet.add("456");
        // Iterator iterator = HashSet.iterator();
        Iterator<String> iterator = HashSet.iterator();                   //返回链表首地址的迭代器,迭代器指向的是下标为0的前一个地址（？）
        boolean b = iterator.hasNext();                                   //判断iterator是否存在下一个元素，返回boolean
        String next = iterator.next();                                    //*先将指针移动到下一个位置，然后再获取值
        while(iterator.hasNext()) {                                       //迭代器遍历
            System.out.println(iterator.next());
        }


        // for (String str: HashSet) {                                       //for循环遍历
        //     System.out.println(str);
        // }
    }
}