package com.example.activiti.vo;


/**
 * 返回对象
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class ResultBody<T> {
    private Integer code;
    private boolean success;
    private T data;

    public ResultBody() {
        this.code = 200;
        this.success = true;
    }

    public ResultBody(T data) {
        this.code = 200;
        this.success = true;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
