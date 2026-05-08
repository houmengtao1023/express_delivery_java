package com.lazy.express.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @descrption: ResultCode 业务结果码
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    OK(0, "ok"),
    FAIL(500, "服务器异常"),
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "资源不存在");

    private final int code;
    private final String msg;
}
