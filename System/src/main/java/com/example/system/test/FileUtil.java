package com.example.system.test;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

public class FileUtil {
    //BASE64解码成File文件
    public File base64ToFile(String base64,String fileName) {
        File file = null;
        //创建文件目录
        String filePath = this.getClass().getClassLoader().getResource("").getPath();
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            //截取base64头部，获取文件类型
            String fileType = Base64FileTypeEnum.getFileType(base64.substring(0, base64.indexOf(",")));
            //去掉头部，防止转换文件后打开显示文件损坏
            String s = base64.substring(base64.indexOf(",") + 1);
            byte[] bytes = new BASE64Decoder().decodeBuffer(s);
            file = new File(filePath + "/" + fileName + fileType);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    //File转MultipartFile
    public MultipartFile getMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE, true, file.getName());
        try (InputStream input = new FileInputStream(file); OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }
}
