package com.baizhi.ly.ascept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//运行时机
@Retention(RetentionPolicy.RUNTIME)
//用在什么上
@Target({ElementType.METHOD})
public @interface Logs {
    String name();
}
