package com.base.test.common.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emmet on 17-3-29.
 * desc:
 */
public class GenerateEntity {

    public static final List<String> ignore_columns = Collections.emptyList();

    //生成实体类
    public static void generate(String entityName, String entityPackage, String entityPath, String databaseName,
								Generate.TableInfo tableInfo) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(entityPackage).append(";\r\n").append("\r\n");

        if (tableInfo.getImportDate()) sb.append("import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
        sb.append("import lombok.Data;\r\n\r\n");
        sb.append("import lombok.experimental.Accessors;\r\n\r\n");
        sb.append("import com.baomidou.mybatisplus.annotation.IdType;\r\n");
        sb.append("import com.baomidou.mybatisplus.annotation.TableField;\r\n");
        sb.append("import com.baomidou.mybatisplus.annotation.TableId;\r\n");
        sb.append("import com.baomidou.mybatisplus.annotation.TableName;\r\n");
        sb.append("import java.io.Serializable;\r\n");
        if (tableInfo.getImportBigDecimal()) sb.append("import java.math.BigDecimal;\r\n");
        if (tableInfo.getImportDate()) sb.append("import java.util.Date;\r\n");
        if (tableInfo.getImportDateTime()) sb.append("import java.util.Date;\r\n");
        sb.append("\r\n");
        sb.append("@TableName(\"").append(tableInfo.getTableName()).append("\")").append("\r\n");
        sb.append("@Accessors(chain = true)\r\n");
        sb.append("@Data\r\n");
        sb.append("public class ").append(entityName).append(" implements Serializable {\r\n");
        for (int i = 0; i < tableInfo.getColnames().size(); i++) {
            if (ignore_columns.contains(tableInfo.getColnames().get(i))) {
                continue;
            }
            sb.append("\t/**\r\n");
            sb.append("\t * ").append(tableInfo.getColComment().get(i)).append("\r\n");
            sb.append("\t */\r\n");
            if ("Date".equals(getType(tableInfo.getColTypes().get(i))))
                sb.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\r\n");
            if (i == 0) {
                if (null != tableInfo.getExtra().get(0) && "auto_increment".equalsIgnoreCase(tableInfo.getExtra().get(0))) {
                    sb.append("\t@TableId(type = IdType.AUTO)\r\n");
                } else {
                    sb.append("\t@TableId(type = IdType.ASSIGN_ID)\r\n");
                }
            } else {
                sb.append("\t@TableField\r\n");
            }
            sb.append("\tprivate ").append(getType(tableInfo.getColTypes().get(i))).append(" ").append(underline2Camel(tableInfo.getColnames().get(i)))
                    .append(";\r\n");
        }
        sb.append("}\r\n");
        String content = sb.toString();
        System.out.println(content);
        FileWriter fw = new FileWriter(entityPath + entityName + ".java");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
    }

    //根据SQL类型获取java类型
    private static String getType(String sqlType) {
        switch (sqlType) {
            case "tinyint":
                return "Integer";
            case "smallint":
                return "Integer";
            case "int":
                return "Integer";
            case "integer":
                return "Integer";
            case "bigint":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "numeric":
                return "Double";
            case "decimal":
                return "BigDecimal";
            case "varchar":
            case "char":
            case "text":
            case "mediumtext":
                return "String";
            case "date":
                return "Date";
            case "time":
            case "datetime":
            case "timestamp":
                return "Date";
            case "json":
                return "String";
            case "bit":
                return "Boolean";
            default:
                System.out.println("ERROR DATA TYPE : " + sqlType);
                return null;
        }
    }

    //把输入字符串的首字母改成大写
    private static String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    //下划线转首字母大写
    public static String underline2Camel(String line) {
        if (line == null || "".equals(line)) return "";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
