package com.example.system.utils;

import com.example.system.entity.PageBean;
import com.example.system.entity.Student1;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class PageUtil {
    public static void main(String[] args) {
//        page1();
        page2();
    }

    /**
     * 手动分页
     */
    public static void page1() {
        List<Student1> list = new ArrayList<>();
        int count = 35;
        for (int i = 1; i < count; i++) {
            Student1 student = new Student1();
            student.setName("张" + i);
            student.setAge(i);
            list.add(student);
        }
        int pageNum = 1;
        int pageSize = 10;

        List<Student1> result = new ArrayList<>();
        if (pageSize * (pageNum - 1) <= list.size()) {
            result = list.subList(pageSize * (pageNum - 1),
                    (pageSize * pageNum) > list.size() ? list.size() : (pageSize * pageNum));
        }
        for (Student1 s : result) {
            log.info("{}", s);
        }
    }

    /**
     * 分页工具
     */
    public static void page2() {
        List<Student1> list = new ArrayList<>();
        int count = 10;
        for (int i = 1; i <= count; i++) {
            Student1 student = new Student1();
            student.setName("张" + i);
            student.setAge(i);
            list.add(student);
        }
        int pageNum = 1;
        int pageSize = 10;
//        PageBean<Student> pageBean = new PageBean<>(list, pageNum, pageSize);
//        log.info("当前集合：{}",pageBean.getList());
//        log.info("当前页码：{}",pageBean.getPageNum());
//        log.info("每页显示行数：{}",pageBean.getPageSize());
//        log.info("总页数：{}",pageBean.getTotalPage());
//        log.info("总行数：{}",pageBean.getTotalRows());
//        log.info("是否有下一页：{}",pageBean.getHasNext());

        PageBean<Student1> pageBean1 = new PageBean<Student1>();
        pageBean1.setPageNum(1);
        pageBean1.setPageSize(10);
        pageBean1.setList(list);
        log.info("当前集合：{}",pageBean1.getList());
        log.info("当前页码：{}",pageBean1.getPageNum());
        log.info("每页显示行数：{}",pageBean1.getPageSize());
        log.info("总页数：{}",pageBean1.getTotalPage());
        log.info("总行数：{}",pageBean1.getTotalRows());
        log.info("是否有下一页：{}",pageBean1.getHasNext());
    }
}
