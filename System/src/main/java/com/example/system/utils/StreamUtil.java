package com.example.system.utils;

import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流工具
 *
 * @author laughlook
 * @date 2022/08/08
 */
public class StreamUtil {
    public static void main(String[] args) {
//        createStream();
//        filterStream();
//        distinctStream();
//        limitStream();
//        skipStream();
        mapStream();
//        flatMapStream();
    }

    /**
     * 创建流
     */
    @SneakyThrows
    public static void createStream() {
        //集合
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = integerList.stream();
        System.out.println(stream);
        //数组
        int[] intArr = {1, 2, 3, 4, 5};
        IntStream stream1 = Arrays.stream(intArr);
        System.out.println(stream1);
        //值
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
        System.out.println(stream2);
        //文件
        Stream<String> stream3 = Files.lines(Paths.get("E:\\data\\111.txt"), Charset.defaultCharset());
        System.out.println(stream3);
        //函数
        Stream<Integer> stream4 = Stream.iterate(1, n -> n + 1).limit(5);
        System.out.println(stream4);
        Stream<Double> stream5 = Stream.generate(Math::random).limit(5);
        System.out.println(stream5);
    }

    /**
     * 过滤
     */
    public static void filterStream() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list = integerList.stream()
                .filter(i -> i > 3)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 去重
     */
    public static void distinctStream() {
        List<Integer> integerList = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        List<Integer> list = integerList.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 指定数量
     */
    public static void limitStream() {
        List<Integer> integerList = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        List<Integer> list = integerList.stream()
                .limit(5)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 跳过元素
     */
    public static void skipStream() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list = integerList.stream()
                .skip(4)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * map映射
     */
    public static void mapStream() {
        List<String> stringList = Arrays.asList("a", "bb", "ccc", "dddd", "fffff");
        List<Integer> list = stringList.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 平面map
     */
    public static void flatMapStream() {
        List<String> stringList = Arrays.asList("a", "b b", "c c c", "d d d d", "f f f f f");
        List<String> list = stringList.stream()
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println(list);
    }
}
