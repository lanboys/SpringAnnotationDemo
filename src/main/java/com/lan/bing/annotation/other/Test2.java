package com.lan.bing.annotation.other;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Created by 蓝兵 on 2018/4/17.
 */

public class Test2 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        //https://blog.csdn.net/jly4758/article/details/44774217
        //https://blog.csdn.net/top_code/article/details/51708043
        //https://blog.csdn.net/bjo2008cn/article/details/53543467
        //http://outofmemory.cn/code-snippet/38544/java-javassist-add-annotation
        //Class<Person> personClass = Person.class;

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.bing.lan.annotation.Person");
        String read = "read";

        Object[] annotations1 = ctClass.getAnnotations();
        for (int i = 0; i < annotations1.length; i++) {
            if (annotations1[i] instanceof StudentAction) {
                StudentAction action = (StudentAction) annotations1[i];
                System.out.println("annotations1: " + action);

                String read1 = action.read();

                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();


                for (CtMethod declaredMethod : declaredMethods) {
                    System.out.println("###################################################");

                    CtClass returnType = declaredMethod.getReturnType();
                    MethodInfo methodInfo = declaredMethod.getMethodInfo();
                    ConstPool cp = methodInfo.getConstPool();

                    //原来有的话 需要获取
                    AnnotationsAttribute visibleTagAttribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);

                    Annotation[] visibleTagAnnotations = visibleTagAttribute.getAnnotations();

                    String studentAction = "com.bing.lan.annotation.StudentAction";
                    Annotation annotation1 = new Annotation(studentAction, cp);
                    annotation1.addMemberValue(read, new StringMemberValue(read1, cp));

                    System.out.println("----------------添加前-------------------");
                    System.out.println("visibleTagAttribute: " + visibleTagAttribute);
                    System.out.println("-----------------------------------");

                    boolean needAddStudentAction = true;

                    for (Annotation ann : visibleTagAnnotations) {
                        System.out.println("visibleTagAnnotation: " + ann);
                        if (studentAction.equals(ann.getTypeName())) {
                            needAddStudentAction = false;
                        }
                    }

                    if (needAddStudentAction) {
                        visibleTagAttribute.addAnnotation(annotation1);
                    }

                    methodInfo.addAttribute(visibleTagAttribute);

                    System.out.println("-----------------添加后------------------");
                    System.out.println("visibleTagAttribute: " + visibleTagAttribute);
                    visibleTagAnnotations = visibleTagAttribute.getAnnotations();
                    System.out.println("-----------------------------------");
                    for (Annotation ann : visibleTagAnnotations) {
                        System.out.println("visibleTagAnnotation: " + ann);
                    }
                    System.out.println("###################################################");
                }

                ctClass.writeFile();
            }
        }
    }
}
