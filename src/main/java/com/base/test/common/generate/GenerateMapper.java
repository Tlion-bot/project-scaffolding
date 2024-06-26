package com.base.test.common.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by emmet on 17-3-29.
 * desc:
 */
public class GenerateMapper {
    //生成Mapper类
    public static void generate(String entityName, String entityPackage, String mapperPackage, String mapperPath,
                                String entitySuffix, String mapperInterfaceName) throws IOException {
        File file = new File(mapperPath + mapperInterfaceName + ".java");
        if (file.exists()) return;

        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(mapperPackage).append(";\r\n").append("\r\n");

        sb.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\r\n");
        sb.append("import ").append(entityPackage).append(".").append(entityName + entitySuffix).append(";\r\n");
        sb.append("import org.apache.ibatis.annotations.Mapper;\r\n\r\n");

        sb.append("@Mapper\r\n");
        sb.append("public interface ").append(mapperInterfaceName).append(" extends BaseMapper<" + entityName + entitySuffix + "> {\r\n");
        sb.append("}\r\n");
        String content = sb.toString();
        System.out.println(content);
        FileWriter fw = new FileWriter(mapperPath + mapperInterfaceName + ".java");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        pw.close();
        fw.close();
    }
}
