package com.example.system.utils;

import java.io.File;

/**
 * 文件工具
 *
 * @author laughlook
 * @date 2022/08/16
 */
public class FileUtil {
    public static void main(String[] args) {

    }

    /**
     * 批量重命名文件
     */
    public static void renameFile() {
        String folderPath = "E:\\data\\";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            String[] fulls = name.split("_full");
            String nameNew = fulls[0] + fulls[1];
            file.renameTo(new File(folderPath + nameNew));
        }
    }
}
