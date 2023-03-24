package com.example.system.test;


import com.example.system.entity.QueryUserListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Demo01 {

    public static void main(String[] args) {
        QueryUserListVO vo = new QueryUserListVO();
        vo.setEvent_type(2);
        Set<String> columnNames = new HashSet<>();
        String table = "123";
        List<String> cityList = Arrays.asList("510100", "510300", "510400", "510500", "510600", "510700", "510800", "510900", "511000", "511100", "511300", "511400", "511500", "511600", "511700", "511800", "511900", "512000", "513200", "513300", "513400");
        List<String> cdList = new ArrayList<>();
        List<String> noCdList = new ArrayList<>();
        for (String cityId : cityList) {
            if (cityId.equals("510100")) {
                cdList.add(cityId);
            } else {
                noCdList.add(cityId);
            }
        }
        getListThread(noCdList, vo, table, columnNames);
    }

    private static List<Map<String, String>> getListThread(List<String> cityList, QueryUserListVO queryVO, String table, Set<String> columnNames) {
        List<Map<String, String>> list = Collections.synchronizedList(new ArrayList<>());
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        //哈希列表
        List<Integer> hashList = new ArrayList<>();
        //线程数
        int threadNum = 0;
        //单条线程大小
        int threadSize = 0;
        //
        if (cityList.size() == 1 && cityList.get(0).equals("510100")) {
            for (int i = 0; i < 50; i++) {
                hashList.add(i);
            }
            threadNum = 5;
            threadSize = hashList.size() % threadNum;
        } else {
            for (int i = 0; i < 10; i++) {
                hashList.add(i);
            }
            threadNum = cityList.size();
        }

        // 定义一个任务集合
        List<Callable<Integer>> taskList = new ArrayList<>();
        Callable<Integer> task = null;
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        if (cityList.size() == 1 && cityList.get(0).equals("510100")) {
            String cityId = cityList.get(0);
            if (3 == queryVO.event_type) {
                log.info("RX成都:任务启动");
//                String startRow = StringUtils.reverse(cityId) + "$" + queryVO.cause_code + "$" + matchNum(queryVO.start_time);
//                String endRow = StringUtils.reverse(cityId) + "$" + queryVO.cause_code + "$" + matchNum(queryVO.end_time);
//                log.info("{STARTROW=>'" + startRow + "',STOPROW=>'" + endRow + "'}");
//                List<Map<String, String>> temp = hBaseService.scanData(table, "detail", startRow, endRow, columnNames);
//                list.addAll(temp);
                System.out.println(cityId);
            } else {
                //单条线程执行列表
                List<Integer> cutList = null;
                log.info("MW成都:多线程任务启动");
                for (int i = 0; i < threadNum; i++) {
                    if (i == threadNum - 1) {
                        cutList = hashList.subList(i * threadSize, hashList.size());
                    } else {
                        cutList = hashList.subList(i * threadSize, (i + 1) * threadSize);
                    }
                    final List<Integer> cutFinalList = cutList;
                    task = new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            for (int i = 0; i < cutFinalList.size(); i++) {
//                                String startRow = queryVO.city_id.toString() + "$" + cutFinalList.get(i) + "$" + queryVO.cause_code + "$" + queryVO.event_type + "$" + matchNum(queryVO.start_time);
//                                String endRow = queryVO.city_id.toString() + "$" + cutFinalList.get(i) + "$" + queryVO.cause_code + "$" + queryVO.event_type + "$" + matchNum(queryVO.end_time);
//                                log.info("{STARTROW=>'" + startRow + "',STOPROW=>'" + endRow + "'}");
//                                List<Map<String, String>> temp = hBaseService.scanData(table, "detail", startRow, endRow, columnNames);
//                                list.addAll(temp);
                                System.out.println(cutFinalList.get(i));
                            }
                            return 1;
                        }
                    };
                    taskList.add(task);
                }
            }
        } else {
            if (3 == queryVO.event_type) {
                log.info("RX非成都:多线程任务启动");
                for (int i = 0; i < cityList.size(); i++) {
                    final int x = i;
                    task = new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
//                            String startRow = StringUtils.reverse(cityList.get(x)) + "$" + queryVO.cause_code + "$" + matchNum(queryVO.start_time);
//                            String endRow = StringUtils.reverse(cityList.get(x)) + "$" + queryVO.cause_code + "$" + matchNum(queryVO.end_time);
//                            log.info("{STARTROW=>'" + startRow + "',STOPROW=>'" + endRow + "'}");
//                            List<Map<String, String>> temp = hBaseService.scanData(table, "detail", startRow, endRow, columnNames);
//                            map.put(cityList.get(x), temp);
                            System.out.println(cityList.get(x));
                            return 1;
                        }
                    };
                    taskList.add(task);
                }
            } else {
                //单条线程执行列表
                List<Integer> cutList = null;
                log.info("MW非成都:多线程任务启动");
                for (int i = 0; i < cityList.size(); i++) {
                    final int x = i;
                    task = new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List<Map<String, String>> total = new ArrayList<>();
                            for (int j = 0; j < hashList.size(); j++) {
//                                String startRow = queryVO.city_id.toString() + "$" + hashList.get(j) + "$" + queryVO.cause_code + "$" + queryVO.event_type + "$" + matchNum(queryVO.start_time);
//                                String endRow = queryVO.city_id.toString() + "$" + hashList.get(j) + "$" + queryVO.cause_code + "$" + queryVO.event_type + "$" + matchNum(queryVO.end_time);
//                                log.info("{STARTROW=>'" + startRow + "',STOPROW=>'" + endRow + "'}");
//                                List<Map<String, String>> temp = hBaseService.scanData(table, "detail", startRow, endRow, columnNames);
//                                total.addAll(temp);
                                System.out.println(hashList.get(j));
                            }
                            map.put(cityList.get(x), total);
                            return 1;
                        }
                    };
                    taskList.add(task);
                }
            }
        }
        try {
            executorService.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executorService.shutdown();
        log.info("线程任务执行结束");
        if (!cityList.get(0).equals("510100")) {
            for (String cityId : cityList) {
                List<Map<String, String>> maps = Collections.synchronizedList(map.get(cityId));
                list.addAll(maps);
            }
        }

        return list;
    }
}
