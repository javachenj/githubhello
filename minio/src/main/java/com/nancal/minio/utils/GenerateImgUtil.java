package com.nancal.minio.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Objects;

/***
 *
 * base64转图片工具类
 */
public class GenerateImgUtil {
    public static String GenerateImage(String imgStr, String aac002) {// 对字节数组字符串进行Base64解码并生成图片

        String imgFilePath = File.separator + "opt" + File.separator + aac002 + ".jpg";
        OutputStream out = null;
        if (imgStr == null) // 图像数据为空
            return null;
        try {
            // Base64解码
            byte[] bytes = new BASE64Decoder().decodeBuffer(imgStr.replaceAll("data:image/jpeg;base64,", ""));
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            return imgFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * @函数功能说明： 本地图片转base64
     */
    public static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        InputStream in = null;
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
        return encoder.encode(Objects.requireNonNull(data));
    }

    public static void main(String[] args) {
        ImageToBase64("\\图片\\u=1573417443,3534004914&fm=15&gp=0.jpg");
    }
}
