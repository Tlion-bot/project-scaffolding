package com.base.test.project.business.controller.manager;


import com.base.test.common.core.domain.AjaxResult;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/doc")
public class poiTlController {

    /**
     * 绩效导出
     * @return
     */
    @GetMapping("/print")
    public AjaxResult performancePrint(HttpServletResponse response, @RequestParam String name) {
            String filename = "绩效考核证明.docx";
            XWPFTemplate template = null;
            BufferedOutputStream bos = null;
            OutputStream out = null;
            try {

                //LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
                //Configure build = Configure.builder().bind(policy, "jixiaoList").build();
               File file = new File("src/main/resources/doc/name.docx");
               //   File file = new File("D:\\template.docx");
                 InputStream inputStream = new FileInputStream(file);

                template = XWPFTemplate.compile(inputStream).render(
                        new HashMap<String, Object>() {{
                            put("name", name);

                        }});

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "utf-8") + "\"");

                out = response.getOutputStream();
                bos = new BufferedOutputStream(out);
                template.write(bos);
                bos.flush();
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                PoitlIOUtils.closeQuietlyMulti(template, bos, out);
            }

            return null;
        //return AjaxResult.success(filename);
        }


}
