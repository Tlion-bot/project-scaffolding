package com.base.test.common.utils.file;

import cn.hutool.core.util.StrUtil;
import com.base.test.common.config.RuoYiConfig;
import com.base.test.common.constant.Constants;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;

/**
 * 图片处理工具类
 *
 * @author ruoyi
 */
public class ImageUtils
{
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] getImage(String imagePath)
    {
        InputStream is = getFile(imagePath);
        try
        {
            return IOUtils.toByteArray(is);
        }
        catch (Exception e)
        {
            log.error("图片加载异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream getFile(String imagePath)
    {
        try
        {
            byte[] result = readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        }
        catch (Exception e)
        {
            log.error("获取图片异常 {}", e);
        }
        return null;
    }

    /**
     * 读取文件为字节数据
     * 
     * @param  地址
     * @return 字节数据
     */
    public static byte[] readFile(String url)
    {
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try
        {
            if (url.startsWith("http"))
            {
                // 网络地址
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                in = urlConnection.getInputStream();
            }
            else
            {
                // 本机地址
                String localPath = RuoYiConfig.getProfile();
                String downloadPath = localPath + StrUtil.subAfter(url, Constants.RESOURCE_PREFIX,false);
                in = new FileInputStream(downloadPath);
            }
            return IOUtils.toByteArray(in);
        }
        catch (Exception e)
        {
            log.error("获取文件路径异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(baos);
        }
    }
    
    
    public static String fileToBase64(File file) {
        String result = null;
        ByteArrayOutputStream out = null;
        try {
            // 获取请求输入流
            InputStream inputStream = new FileInputStream(file);
            // inputStream流数据转ByteArrayOutputStream
            int len = -1;
            byte[] buffer = new byte[1024];
            out = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            result = new String(Base64.getEncoder().encode(out.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	if(out != null) {
        		try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return result;
    }
    
    

    /**
     * 请求图片地址, 返回的结果进行base64编码
     * @param imgUrl 图片地址
     * @return
     */
    public static String requestUrlToBase64(String imgUrl) {
        String result = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(imgUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 获取请求输入流
            InputStream inputStream = connection.getInputStream();
            // inputStream流数据转ByteArrayOutputStream
            int len = -1;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            // ByteArrayOutputStream编码成base64字符串
            result = new String(Base64.getEncoder().encode(out.toByteArray()));
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                connection.disconnect();
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String img = ImageUtils.requestUrlToBase64("http://shop-img.jxminxun.com/63/sku/a308f741-fec4-4029-a0c1-584bb8666fd9.jpg");
        System.out.println(img);
    }


}
