package com.base.test.algorithm;

/**
 * 14. 最长公共前缀
 * @author nnc
 * @date 2023/9/4 18:00
 */
public class LongestCommonPrefix {
     static String longestCommonPrefix(String[] strs) {
        String maxPrefix=strs[0];
        for (int i=1;i<strs.length;i++)
        {
            int j=0;
            for (;j<maxPrefix.length();j++)
            {
                if(maxPrefix.charAt(j)!=strs[i].charAt(j))
                    break;
            }
            maxPrefix=maxPrefix.substring(0,j);
            if(maxPrefix.equals(""))
                return maxPrefix;
        }
        return maxPrefix;
    }

    public static void main(String[] args) {
        // Scanner in=new Scanner(System.in);
        // String testString=in.nextLine();
        String[] testString={"aab","aabc","aaabd"};
        System.out.println(longestCommonPrefix(testString));
    }
}
