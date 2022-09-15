package com.example.demo.entity;

/**
 * 封装分页相关信息
 */

public class Page {

    //当前页码
    private int current = 1;
    //每页显示最大上限
    private int limit = 10;
    //数据总数，用于计算总页数
    private int rows;
    //查询路径，用于复用分页链接
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的起始行
     */
    public int getOffset(){
        return (current - 1) * limit;
    }

    /**
     * 获取总的页数
     */
    public int getTotal(){
        return rows%limit == 0 ? rows/limit : rows/limit+1;
    }

    /**
     * 获取展示的起始页码
     */
    public int getFrom(){
        return Math.max(1, current - 2);
    }

    /**
     * 获取展示的结束页码Ω
     */
    public int getTo(){
        return Math.min(getTotal(), current + 2);
    }

}
