package com.lan.bing.annotation;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * https://www.jianshu.com/p/f43a01513a79
 *
 * @author lan_bing
 * @date 2019-03-28 10:49
 */
public class AliasForTest {

    public static class TestBean {

        @AliasFor("test")
        public void test() {
        }
    }

    @Test
    public void test1() throws NoSuchMethodException {
        Method method = TestBean.class.getMethod("test", null);

        AliasFor aliasFor1 = method.getAnnotation(AliasFor.class);
        AliasFor aliasFor2 = AnnotationUtils.synthesizeAnnotation(aliasFor1, null);
        //AliasFor aliasFor3 = AnnotationUtils.getAnnotation(method, AliasFor.class);

        System.out.println("test1(): ");
    }

    @ContextConfig(value = "simple.xml")
    static class SimpleConfigTestCase {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface ContextConfig {

        @AliasFor("location")
        String value() default "";

        @AliasFor("value")
        String location() default "";

        Class<?> klass() default Object.class;
    }

    @Test
    public void test2() throws NoSuchMethodException {

        ContextConfig contextConfig1 = SimpleConfigTestCase.class.getAnnotation(ContextConfig.class);
        ContextConfig contextConfig2 = AnnotationUtils.synthesizeAnnotation(contextConfig1, null);
        //ContextConfig contextConfig3 = AnnotationUtils.getAnnotation(SimpleConfigTestCase.class, ContextConfig.class);

        System.out.println("test2(): ");
    }
}