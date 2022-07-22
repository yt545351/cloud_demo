package com.example.system.entity;

import java.util.List;

/**
 * 页面豆
 * 分页工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class PageBean<T> {
    /**
     * 存放需显示的实体类数据
     */
    private List<T> list;
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    /**
     * 每页显示的行数
     */
    private Integer pageSize = 10;
    /**
     * 总页
     */
    private Integer totalPage;
    /**
     * 总行数
     */
    private Integer totalRows;
    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    public PageBean() {
    }

    public PageBean(List<T> list, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize * (pageNum - 1) <= list.size()) {
            this.list = list.subList(pageSize * (pageNum - 1),
                    (pageSize * pageNum) > list.size() ? list.size() : (pageSize * pageNum));
            this.pageNum = pageNum;
        } else {
            this.pageNum = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
            this.list = list.subList(pageSize * (this.pageNum - 1),
                    (pageSize * this.pageNum) > list.size() ? list.size() : (pageSize * this.pageNum));
        }
        this.pageSize = pageSize;
        this.totalRows = list.size();
        this.totalPage = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
        this.hasNext = pageSize * pageNum >= list.size() ? false : true;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        if (pageSize * (pageNum - 1) <= list.size()) {
            this.list = list.subList(pageSize * (pageNum - 1),
                    (pageSize * pageNum) > list.size() ? list.size() : (pageSize * pageNum));
        } else {
            this.pageNum = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
            this.list = list.subList(pageSize * (pageNum - 1),
                    (pageSize * pageNum) > list.size() ? list.size() : (pageSize * pageNum));
        }
        this.totalRows = list.size();
        this.totalPage = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
        this.hasNext = pageSize * pageNum >= list.size() ? false : true;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        //如果传入的页码为null或者小于1，就默认给1
        if (pageNum == null || pageNum < 1) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        //如果传入的行数为null或者小于1，就默认给10
        if (pageSize == null || pageSize < 1) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

}
