

import java.io.IOException;

public class Test {

    private static final String EMAIL_REGEX =
            "^[\\p{L}0-9_+&*-]+(?:\\.[\\p{L}0-9_+&*-]+)*@" +
                    "(?:[A-Za-z0-9-]+\\.)+[A-Za-z\\u4e00-\\u9fa5]{2,}$";

    // \\p{L} 匹配任何种类的字母字符，包括中文
    // \\u4e00-\\u9fa5 是Unicode中中文的基本区块

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static void main(String[] args) throws IOException {
        // KillPortUtils.start(8329);


            // 注意：这个正则表达式并不是完美的，但它应该能够匹配许多包含中文字符的邮箱地址



                String email1 = "user@example.com";
                String email2 = "用户@qq.com";
                String email3 = "invalid-email-no-at-sign";
                String email4 = "用户a.name@domain.com";

                System.out.println(isValidEmail(email1) ? "Valid Email" : "Invalid Email"); // Valid Email
                System.out.println(isValidEmail(email2) ? "Valid Email (Chinese)" : "Invalid Email"); // 应该为Valid Email (Chinese)，但可能取决于正则表达式的精确性
                System.out.println(isValidEmail(email3) ? "Valid Email" : "Invalid Email"); // Invalid Email
                System.out.println(isValidEmail(email4) ? "Valid Email" : "Invalid Email"); // Valid Email


    }
}
