package com.yjc.xiaoxin.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 统一结果返回类
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //状态码，1表示成功，0或其他表示失败

    private String msg; //错误信息

    private T data; //返回的数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }


    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}

