package com.yjc.xiaoxin.common;

/**
 * 基于ThreadLocal封装,用于设置和获取当前登陆用户的id
 */
public class BaseContext {
    public static ThreadLocal<Long> tl= new ThreadLocal();
    public static void setId(Long id){
        tl.set(id);
    }

    public static Long getId(){
        return tl.get();
    }
}
