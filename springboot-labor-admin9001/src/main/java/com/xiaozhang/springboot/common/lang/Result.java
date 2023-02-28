package com.xiaozhang.springboot.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: xiaozhangtx
 * @ClassName: Result
 * @Description: TODO 公共结果返回类
 * @date: 2023/2/27 17:42
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;
    private String des;

    public static Result success(String msg) {
        return success(200, msg, null, "");
    }

    public static Result success(int code, String msg, Object data, String des) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setDes(des);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400, msg, null, "");
    }

    public static Result fail(int code, String msg, Object data, String des) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setDes(des);
        return r;
    }
}
