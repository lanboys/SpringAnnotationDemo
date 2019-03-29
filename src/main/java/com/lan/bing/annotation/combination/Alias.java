package com.lan.bing.annotation.combination;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lan_bing
 * @date 2019-03-27 19:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Alias {

    String value() default "";

    String attribute() default "";

    Class<? extends Annotation> annotation() default Annotation.class;
}