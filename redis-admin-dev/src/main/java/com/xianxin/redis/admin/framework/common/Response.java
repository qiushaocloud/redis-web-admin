package com.xianxin.redis.admin.framework.common;

import java.io.Serializable;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 **/
public class Response<T> implements Serializable {

    private int code;

    private String msg;

    private long count = 0;

    private T data;

    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(int code, String msg, long count, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public static final Integer SUCCESS = 200;
    public static final Integer SUCCESS_0 = 0;
    public static final Integer FAILD = 500;
    public static final Integer WORDFAILD = 560;
    public static final Integer PASSWORDFAILD = 550;
    public static final Integer PHONECODEFAILD = 530;

    public static <T> Response<T> success() {
        return buildResponse(SUCCESS, "success", null);
    }

    public static <T> Response<T> success(String msg) {
        return new Response<>(SUCCESS, msg);
    }

    public static <T> Response<T> success(T data) {
        return buildResponse(SUCCESS, "success", data);
    }

    public static <T> Response<T> success(long count, T data) {
        return buildResponse(SUCCESS, "success", count, data);
    }

    public static <T> Response<T> success(int code, String msg) {
        return buildResponse(code, msg, 0, null);
    }

    public static <T> Response<T> error() {
        return buildResponse(555, "error", 0, null);
    }

    public static <T> Response<T> error(int code, String msg) {
        return new Response<T>(code, msg);
    }

    public static <T> Response<T> error(String msg) {
        return buildResponse(555, msg, 0, null);
    }

    public static <T> Response<T> success(int code, long count, T data) {
        return buildResponse(code, "success", count, data);
    }

    public static <T> Response<T> success(int code, String msg, T data) {
        return buildResponse(code, msg, data);
    }

    public static <T> Response<T> success(int code, long count, String msg) {
        return buildResponse(code, msg, count, null);
    }

    public static <T> Response<T> success(int code, long count, String msg, T data) {
        return buildResponse(code, msg, count, data);
    }

    public static <T> Response<T> faild() {
        return buildResponse(FAILD, "source not exist", null);
    }

    public static <T> Response<T> faild(String msg) {
        return buildResponse(FAILD, msg, null);
    }

    public static <T> Response<T> phoneCodeFaild(String msg) {
        return buildResponse(PHONECODEFAILD, msg, null);
    }

    public static <T> Response<T> passWordFaild(String msg) {
        return buildResponse(PASSWORDFAILD, msg, null);
    }

    public static <T> Response<T> wordFaild(String msg) {
        return buildResponse(WORDFAILD, msg, null);
    }

    public static <T> Response<T> faild(String msg, T data) {
        return buildResponse(FAILD, msg, data);
    }

    public static <T> Response<T> buildResponse(int code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> buildResponse(int code, String msg, long count) {
        return new Response<>(code, msg, count, null);
    }

    public static <T> Response<T> buildResponse(int code, String msg, long count, T data) {
        return new Response<>(code, msg, count, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
