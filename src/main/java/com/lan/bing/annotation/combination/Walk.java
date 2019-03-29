package com.lan.bing.annotation.combination;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lan_bing
 * @date 2019-03-27 18:49
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Walk {

    int walk() default 10;
}
