package com.campus.lostfound.common;

import lombok.Data;

/**
 * 统一返回结果封装
 */
@Data
public class Result<T> {
    private Integer code;     // 200成功，其他失败
    private String msg;       // 提示信息
    private T data;           // 数据

    public static <T> Result<T> success() {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        return r;
    }

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
