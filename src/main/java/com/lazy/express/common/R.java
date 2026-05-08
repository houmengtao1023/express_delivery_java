package com.lazy.express.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @descrption: R 统一响应包装
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(ResultCode.OK.getCode());
        r.setMsg(ResultCode.OK.getMsg());
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(String msg) {
        return fail(ResultCode.FAIL.getCode(), msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
