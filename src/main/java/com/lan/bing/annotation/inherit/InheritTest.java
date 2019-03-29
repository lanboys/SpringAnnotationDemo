package com.lan.bing.annotation.inherit;

import java.util.Arrays;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * 注解继承
 *
 * @author lan_bing
 * @date 2019-03-27 17:17
 */
public class InheritTest {

    public static void main(String[] args) throws Exception {
        //派生类也会自动被注解@Hello标记。
        if (InheritBean.class.isAnnotationPresent(Hello.class)) {
            Hello hello = InheritBean.class.getAnnotation(Hello.class);
            System.out.println("Hello: " + hello);
        }

        // ClassPool是对字节码进行处理，所以找不到继承后的注解
        String classname = "com.lan.bing.annotation.inherit.InheritBean";
        printAnnotation(classname);
        classname = "com.lan.bing.annotation.inherit.BaseInherit";
        printAnnotation(classname);
    }

    private static void printAnnotation(String classname) throws NotFoundException, ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(classname);
        Object[] annotations = ctClass.getAnnotations();
        System.out.println("annotations " + Arrays.toString(annotations));
    }
}