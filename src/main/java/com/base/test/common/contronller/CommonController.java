package com.base.test.common.contronller;


import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${upload.path}")
    String basePath;


    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files") MultipartFile files[]) {
        JSONObject object = new JSONObject();
        String fileFullName[] = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            // String fileName = files[i].getOriginalFilename(); // 文件名
            //原始文件名(不使用原始的文件名防止文件覆盖)
            String originalFilename = files[i].getOriginalFilename();
            //获取文件名的后缀，如获取.jpg后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID重新生成文件名，防止文件名重复造成图片覆盖
            String fileName = UUID.randomUUID().toString() + suffix;

            File dest = new File(basePath + '/' + fileName);

            fileFullName[i]=basePath + '/' + fileName;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                log.error("{}", e);
                object.put("success", 2);
                object.put("result", "程序错误，请重新上传");
                return object.toString();
            }
        }
        object.put("success", 1);
        object.put("result", "文件上传成功");
        object.put("文件名和路径", fileFullName);
        return object.toString();
    }


// @PostMapping("/upload")
// public String upload(MultipartFile myFile) throws IOException {
//     //此时的myFile是一个临时文件，需要转到指定的位置，不然本次请求完成后临时文件会被删除
//
//     //原始文件名(不使用原始的文件名防止文件覆盖)
//     String originalFilename = myFile.getOriginalFilename();
//     //获取文件名的后缀，如获取.jpg后缀
//     String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//     //使用UUID重新生成文件名，防止文件名重复造成图片覆盖
//     String fileName = UUID.randomUUID().toString() + suffix;
//
//     //如果目录是多级的，那么需要创建目录
//     //创建一个目录对象
//     File dir = new File(basePath);
//     //判断当前目录是否存在，如果不存在那么创建该目录
//     if (!dir.exists()) {
//         dir.mkdir();
//     }
//     myFile.transferTo(new File(basePath + fileName));
//     return "sucess";
// }

// @PostMapping("/download")
// public String downLoad(@RequestParam String name, HttpServletResponse response) {
//     try {
//         //输入流，通过输入流将读取文件内容
//         FileInputStream fileInputStream = new FileInputStream(basePath + name);
//         //输出流，通过输出流将文件回写到浏览器，在浏览器展示图片
//         ServletOutputStream outputStream = response.getOutputStream();
//         response.setContentType("image/jpeg");
//         int len = 0;
//         byte[] bytes = new byte[1024];
//         while (fileInputStream.read(bytes) != -1) {
//             outputStream.write(bytes, 0, len);
//         }
//         //关闭流
//         fileInputStream.close();
//         outputStream.close();
//         return "sucess";
//     } catch (Exception e) {
//         e.printStackTrace();
//         return "flase";
//     }
// }


    @GetMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName) {
        File file = new File(basePath + fileName);
        if (!file.exists()) {
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }


}