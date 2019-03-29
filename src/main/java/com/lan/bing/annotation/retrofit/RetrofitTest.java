package com.lan.bing.annotation.retrofit;

import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Created by 蓝兵 on 2018/4/18
 * //https://blog.csdn.net/jly4758/article/details/44774217
 * //https://blog.csdn.net/top_code/article/details/51708043
 * //https://blog.csdn.net/bjo2008cn/article/details/53543467
 * //http://outofmemory.cn/code-snippet/38544/java-javassist-add-annotation
 * //Class<Person> personClass = Person.class;
 */

public class RetrofitTest {

    public static void main(String[] args) {
        modifyClass("com.lan.bing.annotation.retrofit.ApiService");
    }

    private static void modifyClass(String apiServiceName) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(apiServiceName);
            Object[] annotations = ctClass.getAnnotations();
            for (Object annotation : annotations) {
                // 只对 AllHeaders 做处理
                if (annotation instanceof AllHeaders) {
                    String[] value = ((AllHeaders) annotation).value();
                    System.out.println("AllHeaders 注解值value : " + Arrays.toString(value));
                    CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                    for (CtMethod declaredMethod : declaredMethods) {
                        System.out.println("方法名: " + declaredMethod.getName());
                        MethodInfo methodInfo = declaredMethod.getMethodInfo();
                        ConstPool cp = methodInfo.getConstPool();
                        //原来有的话 需要获取
                        AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) methodInfo
                                .getAttribute(AnnotationsAttribute.visibleTag);
                        if (annotationsAttribute == null) {
                            annotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
                        }
                        Annotation[] attributeAnnotations = annotationsAttribute.getAnnotations();

                        boolean needAdd = true;
                        System.out.println("添加前-注解: " + Arrays.toString(attributeAnnotations));

                        // 已经有Header注解, 跳过添加
                        for (Annotation ann : attributeAnnotations) {
                            if (Headers.class.getName().equals(ann.getTypeName())) {
                                needAdd = false;
                            }
                        }

                        if (needAdd) {
                            // 将类上注解 添加到方法上
                            String name = Headers.class.getName();
                            Annotation annotation1 = new Annotation(name, cp);
                            StringMemberValue[] elements = new StringMemberValue[value.length];
                            for (int i = 0; i < value.length; i++) {
                                elements[i] = new StringMemberValue(value[i], cp);
                            }
                            ArrayMemberValue amv = new ArrayMemberValue(cp);
                            amv.setValue(elements);
                            annotation1.addMemberValue("value", amv);
                            annotationsAttribute.addAnnotation(annotation1);
                        }

                        // 将属性重新设置回去
                        methodInfo.addAttribute(annotationsAttribute);

                        attributeAnnotations = annotationsAttribute.getAnnotations();
                        System.out.println("添加后-注解: " + Arrays.toString(attributeAnnotations));
                    }
                }
            }

            // 添加方法
            ctClass.addMethod(getMethod(ctClass));

            ctClass.writeFile("classDir");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CtMethod getMethod(CtClass ctClass) throws CannotCompileException {
        CtMethod ctMethod = CtMethod.make("public void funAdd(){}", ctClass);

        MethodInfo methodInfo = ctMethod.getMethodInfo();
        ConstPool cp = methodInfo.getConstPool();

        String studentAction = Headers.class.getName();
        Annotation annotation1 = new Annotation(studentAction, cp);
        String[] value = new String[]{"Domain-Name:http://www.baidu.com"};
        StringMemberValue[] elements = new StringMemberValue[value.length];
        for (int i = 0; i < value.length; i++) {
            elements[i] = new StringMemberValue(value[i], cp);
        }
        ArrayMemberValue amv = new ArrayMemberValue(cp);
        amv.setValue(elements);
        annotation1.addMemberValue("value", amv);

        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        annotationsAttribute.addAnnotation(annotation1);

        methodInfo.addAttribute(annotationsAttribute);
        return ctMethod;
    }
}
