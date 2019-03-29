package com.lan.bing.annotation.combination;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lan_bing
 * @date 2019-03-27 18:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Run
@Walk
public @interface Exercise {

    @Alias(annotation = Run.class)
    int run() default 100;

    @Alias(annotation = Walk.class)
    int walk() default 10;
}
