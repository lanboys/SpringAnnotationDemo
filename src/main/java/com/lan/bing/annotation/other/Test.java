package com.lan.bing.annotation.other;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Created by 蓝兵 on 2018/4/17.
 */

public class Test {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        //https://blog.csdn.net/jly4758/article/details/44774217
        //https://blog.csdn.net/top_code/article/details/51708043
        //https://blog.csdn.net/bjo2008cn/article/details/53543467
        //http://outofmemory.cn/code-snippet/38544/java-javassist-add-annotation
        //Class<Person> personClass = Person.class;

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.bing.lan.annotation.Person");

        Object[] annotations1 = ctClass.getAnnotations();
        for (int i = 0; i < annotations1.length; i++) {
            if (annotations1[i] instanceof StudentAction) {
                StudentAction action = (StudentAction) annotations1[i];
                System.out.println("annotations1: " + action);
            }
        }

        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

        for (CtMethod declaredMethod : declaredMethods) {
            System.out.println("###################################################");

            MethodInfo methodInfo = declaredMethod.getMethodInfo();
            ConstPool cp = methodInfo.getConstPool();

            //原来有的话 需要获取
            AnnotationsAttribute visibleTagAttribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
            AnnotationsAttribute invisibleTagAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.invisibleTag);

            Annotation[] visibleTagAnnotations = visibleTagAttribute.getAnnotations();
            Annotation[] invisibleTagAnnotations = invisibleTagAttribute.getAnnotations();

            String studentAction = "com.bing.lan.annotation.StudentAction";
            Annotation annotation1 = new Annotation(studentAction, cp);
            annotation1.addMemberValue("read", new StringMemberValue("kkkk", cp));

            String teacherAction = "com.bing.lan.annotation.TeacherAction";
            Annotation annotation2 = new Annotation(teacherAction, cp);
            annotation2.addMemberValue("teach", new StringMemberValue("teachteach", cp));

            System.out.println("----------------添加前-------------------");
            System.out.println("visibleTagAttribute: " + visibleTagAttribute);
            System.out.println("invisibleTagAttribute: " + invisibleTagAttribute);
            System.out.println("-----------------------------------");

            boolean needAddStudentAction = true;

            for (Annotation ann : visibleTagAnnotations) {
                System.out.println("visibleTagAnnotation: " + ann);
                if (studentAction.equals(ann.getTypeName())) {
                    needAddStudentAction = false;
                }
            }
            for (Annotation ann : invisibleTagAnnotations) {
                System.out.println("invisibleTagAnnotation: " + ann);
            }

            if (needAddStudentAction) {
                visibleTagAttribute.addAnnotation(annotation1);
            }
            //visibleTagAttribute.addAnnotation(annotation2);//可同时添加
            invisibleTagAttribute.addAnnotation(annotation2);

            methodInfo.addAttribute(visibleTagAttribute);
            methodInfo.addAttribute(invisibleTagAttribute);

            System.out.println("-----------------添加后------------------");
            System.out.println("visibleTagAttribute: " + visibleTagAttribute);
            System.out.println("invisibleTagAttribute: " + invisibleTagAttribute);
            visibleTagAnnotations = visibleTagAttribute.getAnnotations();
            invisibleTagAnnotations = invisibleTagAttribute.getAnnotations();
            System.out.println("-----------------------------------");
            for (Annotation ann : visibleTagAnnotations) {
                System.out.println("visibleTagAnnotation: " + ann);
            }
            for (Annotation ann : invisibleTagAnnotations) {
                System.out.println("invisibleTagAnnotation: " + ann);
            }
            System.out.println("-----------------------------------");

            //获取属性值
            List<AttributeInfo> attributes = methodInfo.getAttributes();
            System.out.println("AttributeInfo: " + attributes.size());
            for (AttributeInfo annotationsAttribute : attributes) {
                System.out.println("AttributeInfo: " + annotationsAttribute.getName());
            }
            System.out.println("###################################################");
        }

        ctClass.writeFile();

        Class aClass = ctClass.toClass();
        Method[] declaredMethods1 = aClass.getDeclaredMethods();
        //Method[] declaredMethods1 = Person.class.getDeclaredMethods();
        for (int i = 0; i < declaredMethods1.length; i++) {
            Method method = declaredMethods1[i];
            java.lang.annotation.Annotation[] annotations = method.getAnnotations();
            System.out.println("annotations.length: " + annotations.length);
        }

        //========================================================================
        //========================================================================
        //========================================================================
        testCat();
    }

    private static void testCat() throws NoSuchFieldException, IllegalAccessException {
        Class<Cat> catClass = Cat.class;
        Method[] methods = catClass.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            if (method.isAnnotationPresent(BrotherAction.class)) {

                BrotherAction annotation = method.getAnnotation(BrotherAction.class);

                InvocationHandler h = Proxy.getInvocationHandler(annotation);
                // 获取 AnnotationInvocationHandler 的 memberValues 字段
                Field hField = h.getClass().getDeclaredField("memberValues");
                hField.setAccessible(true);

                Map memberValues = (Map) hField.get(h);
                memberValues.put("brother", "8");

                // 获取 foo 的 value 属性值
                String value = annotation.brother();
                System.out.println(value);
            }
        }
    }
}
