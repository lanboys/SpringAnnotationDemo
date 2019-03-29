package com.lan.bing.annotation.retrofit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by 蓝兵 on 2018/4/18.
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Headers {

    String[] value();
}
