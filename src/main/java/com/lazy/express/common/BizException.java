package com.lazy.express.common;

import lombok.Getter;

/**
 * @descrption: BizException 业务异常
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;

    public BizException(String msg) {
        this(ResultCode.FAIL.getCode(), msg);
    }

    public BizException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
