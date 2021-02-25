package com.nancal.common.utils;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;


/***
 * 
 * @类功能说明：base64转图片工具类

 */
public class GenerateImgUtil {
	 public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null) // 图像数据为空
	            return false;
	        try {
	            // Base64解码
	            byte[] bytes = new BASE64Decoder().decodeBuffer(imgStr.replaceAll("data:image/jpeg;base64,", ""));
	            for (int i = 0; i < bytes.length; ++i) {
	                if (bytes[i] < 0) {// 调整异常数据
	                    bytes[i] += 256;
	                }
	            }
	            // 生成jpeg图片
	            OutputStream out = new FileOutputStream(imgFilePath);
	            out.write(bytes);
	            out.flush();
	            out.close();
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
}
