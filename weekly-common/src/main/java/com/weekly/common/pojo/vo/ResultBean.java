package com.weekly.common.pojo.vo;

public class ResultBean<T> {

    private Integer status;

    private String message;

    private T data;

    private Long total;

    private ResultBean() { }

    private ResultBean(Integer status, String message, T data) {
        this(status, message, data, null);
    }

    private ResultBean(Integer status, String message, T data, Long total) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public static ResultBean<Object> success() {
        return new ResultBean<>(0, "成功", null);
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(0, "成功", data);
    }

    public static <T> ResultBean<T> success(T data, Long total) {
        return new ResultBean<>(0, "成功", data, total);
    }

    public Integer getStatus() {
        return status;
    }

    public ResultBean<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultBean<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultBean<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public ResultBean<T> setTotal(Long total) {
        this.total = total;
        return this;
    }
}
