package com.personnel.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Encoder;
/**
 * @author: young
 * @project_name: svn
 * @description:
 * @date: Created in 2018-12-06  18:16
 * @modified by:
 */
public class Img2Base64Util {

    /***
     *
     * 将图片转换为Base64<br>
     * 将base64编码字符串解码成img图片
     * @param imgFile
     * @return
     */
    public static String getImgStr(String imgFile){
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgFile);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        Encoder encoder = Base64.getEncoder();
        String base64 =  encoder.encodeToString(data.toByteArray());
        base64 = base64.replace("+","+");
        return base64;
    }

    public static void main(String[] args) {
        String str = getImgStr("http://10.32.250.84:8089/uploadFile/3/1/1544246450024_下载.jpg");
        System.out.println(str);
    }
}
