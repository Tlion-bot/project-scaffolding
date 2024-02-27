package com.base.test.java.sty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iterator01 {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Books("红楼梦","曹雪芹",25.8));
        list.add(new Books("西游记","吴承恩",30.8));
        list.add(new Books("水浒传","施耐庵",28.8));
        list.add(new Books("三国演义","罗贯中",25.8));
        Iterator iterator = list.iterator();  //得到一个集合的迭代器
        while (iterator.hasNext()) {   //hasNext()：判断是否还有下一个元素
            //next编译类型Objetc,运行类型取决于真正存放的类型
            Object next =  iterator.next();  //1.指针下移 2.将下移以后集合位置上的元素返回
            System.out.println(next);  //输出得到元素
        }

        //二次遍历需要重置对象
        System.out.println("============二次遍历============");
        iterator = list.iterator();  //得到一个集合的迭代器
        while (iterator.hasNext()) {   //hasNext()：判断是否还有下一个元素
            //next编译类型Objetc,运行类型取决于真正存放的类型
            Object next =  iterator.next();  //1.指针下移 2.将下移以后集合位置上的元素返回
            System.out.println(next);  //输出得到元素
        }
    }
    //快捷键： while --> itit
    //快捷键： 显示所有快捷方式 --> Ctrl + j
}

class Books{
    private String name;
    private String author;
    private double price;

    public Books(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Books{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}