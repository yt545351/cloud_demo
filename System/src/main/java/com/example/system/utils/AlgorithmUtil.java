package com.example.system.utils;

/**
 * 算法
 *
 * @author laughlook
 * @date 2022/09/26
 */
public class AlgorithmUtil {
    public static void main(String[] args) {
        int[] array = {5, 4, 3, 2, 1, 6, 7, 8, 9, 0};
//        binarySearch(array, 5);
//        bubbleSort(array);
        int[] ints = insertionSort(array);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    /**
     * 二分查找
     *
     * @param array  数组
     * @param target 目标
     * @return boolean
     */
    public static boolean binarySearch(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (target == array[middle]) {
                return true;
            } else if (target < array[middle]) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return false;
    }

    /**
     * 冒泡排序
     *
     * @param array 数组
     * @return {@link int[]}
     */
    public static int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] >= array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 插入排序
     *
     * @param array 数组
     * @return {@link int[]}
     */
    public static int[] insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            //插入的数
            int insertVal = array[i];
            //被插入的索引
            int index = i - 1;
            while (index >= 0 && insertVal < array[index]) {
                array[index + 1] = array[index];
                index--;
            }
            array[index + 1] = insertVal;
        }
        return array;
    }
}
