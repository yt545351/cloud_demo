package com.example.system.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.example.system.entity.Animal;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
    public static void main(String[] args) {
//        createFile1();
//        createFile2();
//        appendContent();
//        readFile1();
//        readFile2();
//        writeExcel();
//        readExcel();
        readFile();
    }

    @SneakyThrows
    public static void createFile1() {
        String filePath = "E:\\aaa.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        String content = "这是txt文件";
        FileWriter fw = new FileWriter(filePath);
        fw.write(content);
        fw.close();
    }

    @SneakyThrows
    public static void createFile2() {
        String filePath = "E:\\aaa.txt";
        Path path = Paths.get(filePath);
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        String str = "123111";
        writer.write(str + "\n");
        writer.close();
    }

    @SneakyThrows
    public static void appendContent() {
        String filePath = "E:\\aaa.txt";
        Path path = Paths.get(filePath);
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        String str = "123111";
        writer.write(str + "\n");
        writer.close();
    }

    @SneakyThrows
    public static void readFile1() {
        String filePath = "E:\\aaa.txt";
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String ss = null;
        while ((ss = br.readLine()) != null) {
            System.out.println(ss);
        }
    }

    @SneakyThrows
    public static void readFile2() {
        String filePath = "E:\\aaa.txt";
        List<String> stringList = Files.readAllLines(Paths.get(filePath));
        for (String s : stringList) {
            System.out.println(s);
        }
    }

    @SneakyThrows
    public static void writeExcel() {
        List<Animal> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Animal animal = new Animal();
            animal.setName("dog" + i);
            animal.setSex("公");
            animal.setAge(5 + i);
            list.add(animal);
        }
        EasyExcel.write(new FileOutputStream(new File("E:\\1.xls"))).head(Animal.class).sheet("宠物列表").doWrite(list);
    }

    @SneakyThrows
    public static void readExcel() {
        List<Object> objects = EasyExcel.read(new FileInputStream(new File("E:\\1.xls"))).head(Animal.class).doReadAllSync();
        for (Object object : objects) {
            System.out.println(object);
        }

    }


    @SneakyThrows
    public static void readFile() {
        BufferedReader br = new BufferedReader(new FileReader("E:\\a.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\b.txt"));
        String ss = null;
        while ((ss = br.readLine()) != null) {
            bw.write(ss);
            bw.newLine();
        }
        br.close();
        bw.close();

    }
}