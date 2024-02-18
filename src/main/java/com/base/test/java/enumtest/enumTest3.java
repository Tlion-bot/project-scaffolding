package com.base.test.java.enumtest;


/**
 * @ClassName: MyEnum
 * @Description: Enum learning
 * @author: Lxy
 * @date: 2021/9/23 11:53
 */

public class enumTest3 {
    enum famulei {
        /**
         * famulei:伐木累
         */
        ZHANGSAN, LISI, WANGWU;
    }
    public famulei NameChangedEvent(){
        famulei fml = famulei.LISI;
        switch (fml){
            case ZHANGSAN:
                fml= famulei.LISI;
                break;
            case LISI:
                fml=famulei.WANGWU;
                break;
            case WANGWU:
                fml=famulei.ZHANGSAN;
                break;
            default:
                System.out.println("Error!");
        }
        return fml;
    }

    public static void main(String[] args) {
        enumTest3 myEnum =new enumTest3();
        System.out.println(myEnum.NameChangedEvent());
    }
}

