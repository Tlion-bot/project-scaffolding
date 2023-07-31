package com.base.test.common.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by emmet on 17-3-29.
 * desc:
 */
public class GenerateService {

    //生成Service类
    public static void generate(String entityName, String entityPackage, String mapperPackage, String servicePackage,
                                String servicePath, String serviceInterfacePackage, String entitySuffix,
                                String serviceClassName, String serviceInterfaceName, String mapperInterfaceName) throws IOException {
        File file = new File(servicePath + serviceClassName + ".java");
        if (file.exists()) return;

        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(servicePackage).append(";\r\n").append("\r\n");

        sb.append("import ").append(entityPackage).append(".").append(entityName + entitySuffix).append(";\r\n\r\n");
        sb.append("import ").append(mapperPackage).append(".").append(mapperInterfaceName).append(";\r\n");
        sb.append("import ").append(serviceInterfacePackage).append(".").append(serviceInterfaceName).append(";\r\n");
        sb.append("import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\r\n");
        sb.append("import com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\n");
        sb.append("import lombok.extern.slf4j.Slf4j;\r\n");
        sb.append("import lombok.RequiredArgsConstructor;\r\n");
        sb.append("import org.springframework.stereotype.Service;\r\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\r\n\r\n");

        sb.append("@Slf4j\r\n");
        sb.append("@RequiredArgsConstructor\r\n");
        sb.append("@Transactional(rollbackFor = Exception.class)\r\n");
        sb.append("@Service\r\n");
        sb.append("public class ").append(serviceClassName).append(" extends ServiceImpl<" + mapperInterfaceName + ", " + entityName + entitySuffix + "> implements " + serviceInterfaceName + " {\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic Page<Long> pageList() {\r\n");
        sb.append("\t\treturn null;\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic Long detail(Long id) {\r\n");
        sb.append("\t\treturn null;\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic Long add() {\r\n");
        sb.append("\t\treturn null;\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic void edit() {\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        sb.append("\t@Override\r\n");
        sb.append("\tpublic void delete(Long[] ids) {\r\n");
        sb.append("\t}\r\n");
        sb.append("}\r\n");
        String content = sb.toString();
        System.out.println(content);
        FileWriter fw = new FileWriter(servicePath + serviceClassName + ".java");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        pw.close();
    }

    //把输入字符串的首字母改成小写
    private static String initial(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') ch[0] = (char) (ch[0] + 32);
        return new String(ch);
    }

}
