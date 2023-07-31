package com.base.test.common.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateXml {

    public static void generate(String entityName, String mapperPackage, String xmlPath, String mapperInterfaceName, String xmlFileName) throws IOException {
        File file = new File(xmlPath + xmlFileName + ".xml");
        if (file.exists()) return;

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");

        sb.append("<mapper namespace=\"" + mapperPackage + "." + mapperInterfaceName + "\">\r\n");
        sb.append("</mapper>");
        String content = sb.toString();
        System.out.println(content);
        FileWriter fw = new FileWriter(xmlPath + xmlFileName + ".xml");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        pw.close();
        fw.close();
    }

}
