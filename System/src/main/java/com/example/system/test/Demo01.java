package com.example.system.test;

import java.io.File;

public class Demo01 {

    public static void main(String[] args) {
        String folderPath="E:\\dataJson\\province\\";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            String[] fulls = name.split("0000");
            String nameNew=name+".json";
            System.out.println(nameNew);
            file.renameTo(new File(folderPath+nameNew));
        }
    }

}
