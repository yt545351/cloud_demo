package com.example.system.test;

import cn.hutool.core.codec.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

public class Demo02 {
    public static void main(String[] args) throws FileNotFoundException {
        String src = "C:\\Users\\54535\\Desktop\\P30329-102302.jpg";
        String base64 = convertFileToBase64("data:image/png;base64,", src);
        System.out.println(base64);
//        invoiceBase64ToFile(base64, "222.jpg", "E:\\");
//        MultipartFile multipartFile = base64Convert(base64);
//        System.out.println(multipartFile.getOriginalFilename());
    }

    public static String convertFileToBase64(String format, String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            System.out.println("文件大小（字节）=" + in.available());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组进行Base64编码，得到Base64编码的字符串
        String base64Str = Base64.encode(data);
        return format + base64Str;
    }

    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(",");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(baseStrs[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64DecodeMultipartFile(b, baseStrs[0]);
    }

//    public static void invoiceBase64ToFile(String base64, String fileName, String savePath) {
//        File file = null;
//        //创建文件目录
//        String filePath = savePath;
//        File dir = new File(filePath);
//        if (!dir.exists() && !dir.isDirectory()) {
//            dir.mkdirs();
//        }
//        BufferedOutputStream bos = null;
//        java.io.FileOutputStream fos = null;
//        try {
//            byte[] bytes = Base64.getMimeDecoder().decode(base64);
//            file = new File(filePath + fileName);
//            fos = new java.io.FileOutputStream(file);
//            bos = new BufferedOutputStream(fos);
//            bos.write(bytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (bos != null) {
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}



